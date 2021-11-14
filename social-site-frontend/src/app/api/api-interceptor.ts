import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { UserService } from "../services/user.service";

/**
 * source: https://www.npmjs.com/package/ng-openapi-gen
 */
@Injectable()
export class ApiInterceptor implements HttpInterceptor {

    constructor(private userService: UserService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Apply the headers
        req = req.clone({
            setHeaders: {
                'Authorization': `Bearer ${this.userService.getAccessToken()}`
            }
        });

        // Also handle errors globally
        return next.handle(req).pipe(
            tap(x => x, err => {
                this.userService.refreshLogin()
                    .then(() => {
                        req = req.clone({
                            setHeaders: {
                                'Authorization': `Bearer ${this.userService.getAccessToken()}`
                            }
                        });
                    })
                    .catch(() => console.error(`Error performing request, status code = ${err.status}`))
            })
        );
    }
}