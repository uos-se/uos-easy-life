/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Course } from '../models/Course';
import type { CoursePlan } from '../models/CoursePlan';
import type { LoginDTO } from '../models/LoginDTO';
import type { User } from '../models/User';
import type { UserAcademicStatusDTO } from '../models/UserAcademicStatusDTO';
import type { UserFullInfo } from '../models/UserFullInfo';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class ControllerService {

    /**
     * @param session
     * @returns CoursePlan OK
     * @throws ApiError
     */
    public static getCoursePlan(
        session: string,
    ): CancelablePromise<Array<CoursePlan>> {
        return __request({
            method: 'GET',
            path: `/api/user/course-plan`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @param session
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static setCoursePlan(
        session: string,
        requestBody: Array<CoursePlan>,
    ): CancelablePromise<any> {
        return __request({
            method: 'POST',
            path: `/api/user/course-plan`,
            query: {
                'session': session,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param requestBody
     * @returns string OK
     * @throws ApiError
     */
    public static login(
        requestBody: LoginDTO,
    ): CancelablePromise<string> {
        return __request({
            method: 'POST',
            path: `/api/login`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param session
     * @returns User OK
     * @throws ApiError
     */
    public static getUser(
        session: string,
    ): CancelablePromise<User> {
        return __request({
            method: 'GET',
            path: `/api/user`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @param session
     * @param portalId
     * @param portalPassword
     * @returns any OK
     * @throws ApiError
     */
    public static syncUser(
        session: string,
        portalId: string,
        portalPassword: string,
    ): CancelablePromise<any> {
        return __request({
            method: 'GET',
            path: `/api/user/sync`,
            query: {
                'session': session,
                'portalId': portalId,
                'portalPassword': portalPassword,
            },
        });
    }

    /**
     * @param session
     * @returns Course OK
     * @throws ApiError
     */
    public static recommendCourse(
        session: string,
    ): CancelablePromise<Array<Course>> {
        return __request({
            method: 'GET',
            path: `/api/user/recommended-course`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @param session
     * @returns UserFullInfo OK
     * @throws ApiError
     */
    public static getUserFullInfo(
        session: string,
    ): CancelablePromise<UserFullInfo> {
        return __request({
            method: 'GET',
            path: `/api/user/full`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @param session
     * @returns UserAcademicStatusDTO OK
     * @throws ApiError
     */
    public static getUserAcademicStatus(
        session: string,
    ): CancelablePromise<UserAcademicStatusDTO> {
        return __request({
            method: 'GET',
            path: `/api/user/academic-status`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @param session
     * @returns any OK
     * @throws ApiError
     */
    public static logout(
        session: string,
    ): CancelablePromise<any> {
        return __request({
            method: 'GET',
            path: `/api/logout`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @param session
     * @returns boolean OK
     * @throws ApiError
     */
    public static check(
        session: string,
    ): CancelablePromise<boolean> {
        return __request({
            method: 'GET',
            path: `/api/check`,
            query: {
                'session': session,
            },
        });
    }

    /**
     * @returns string OK
     * @throws ApiError
     */
    public static index(): CancelablePromise<string> {
        return __request({
            method: 'GET',
            path: `/api/`,
        });
    }

}