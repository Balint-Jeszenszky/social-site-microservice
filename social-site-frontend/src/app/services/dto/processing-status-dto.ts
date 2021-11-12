export enum MediaStatusEnum { PROCESSING, FAILED, AVAILABLE }

export interface ProcessingStatusDto {
    status: string;
    progress: number;
    name?: string
}
