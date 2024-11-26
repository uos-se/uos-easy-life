/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import { CancelablePromise } from "../core/CancelablePromise";
import { request as __request } from "../core/request";
import type { User } from "../models/User";
import type { UserAcademicStatusDTO } from "../models/UserAcademicStatusDTO";
import type { UserFullInfo } from "../models/UserFullInfo";

export class MockControllerService {
  /**
   * @param portalId
   * @param portalPassword
   * @returns string OK
   * @throws ApiError
   */
  public static login(
    portalId: string,
    portalPassword: string
  ): CancelablePromise<string> {
    return __request({
      method: "POST",
      path: `/api/login`,
      query: {
        portalId: portalId,
        portalPassword: portalPassword,
      },
    });
  }

  /**
   * @param session
   * @returns User OK
   * @throws ApiError
   */
  public static getUser(session: string): CancelablePromise<User> {
    return __request({
      method: "GET",
      path: `/api/user`,
      query: {
        session: session,
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
    portalPassword: string
  ): CancelablePromise<any> {
    return __request({
      method: "GET",
      path: `/api/user/sync`,
      query: {
        session: session,
        portalId: portalId,
        portalPassword: portalPassword,
      },
    });
  }

  /**
   * @param session
   * @returns UserFullInfo OK
   * @throws ApiError
   */
  public static async getUserFullInfo(
    session: string
  ): Promise<CancelablePromise<UserFullInfo>> {
    return __request({
      method: "GET",
      path: `/api/user/full`,
      query: {
        session: session,
      },
    });
  }

  /**
   * @param session
   * @returns UserAcademicStatusDTO OK
   * @throws ApiError
   */
  public static async getUserAcademicStatus(
    session: string
  ): Promise<CancelablePromise<UserAcademicStatusDTO>> {
    const mockUserAcademicStatus: UserAcademicStatusDTO = {
      totalRequiredCredit: 120,
      totalCompletedCredit: 90,
      majorRequiredCredit: 60,
      majorCompletedCredit: 45,
      majorEssentialRequiredCredit: 30,
      majorEssentialCompletedCredit: 20,
      liberalRequiredCredit: 30,
      liberalCompletedCredit: 25,
      liberalEssentialRequiredCredit: 15,
      liberalEssentialCompletedCredit: 10,
      engineeringRequiredCredit: 40,
      engineeringCompletedCredit: 30,
      generalRequiredCredit: 20,
      generalCompletedCredit: 15,
      minimumTotalGradePointAverage: 2.0,
      totalGradePointAverage: 3.5,
    };
    await new Promise((resolve) => setTimeout(resolve, 1000));
    return new CancelablePromise((resolve, reject) => {
      resolve(mockUserAcademicStatus);
    });
  }

  /**
   * @param session
   * @returns any OK
   * @throws ApiError
   */
  public static logout(session: string): CancelablePromise<any> {
    return __request({
      method: "GET",
      path: `/api/logout`,
      query: {
        session: session,
      },
    });
  }

  /**
   * @param session
   * @returns boolean OK
   * @throws ApiError
   */
  public static check(session: string): CancelablePromise<boolean> {
    return __request({
      method: "GET",
      path: `/api/check`,
      query: {
        session: session,
      },
    });
  }

  /**
   * @returns string OK
   * @throws ApiError
   */
  public static index(): CancelablePromise<string> {
    return __request({
      method: "GET",
      path: `/api/`,
    });
  }
}
