/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
import type { Course } from './Course';
import {
    CourseFromJSON,
    CourseFromJSONTyped,
    CourseToJSON,
    CourseToJSONTyped,
} from './Course';

/**
 * 
 * @export
 * @interface UserFullInfo
 */
export interface UserFullInfo {
    /**
     * 
     * @type {string}
     * @memberof UserFullInfo
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof UserFullInfo
     */
    studentId?: string;
    /**
     * 
     * @type {string}
     * @memberof UserFullInfo
     */
    email?: string;
    /**
     * 
     * @type {string}
     * @memberof UserFullInfo
     */
    phone?: string;
    /**
     * 
     * @type {string}
     * @memberof UserFullInfo
     */
    major?: string;
    /**
     * 
     * @type {number}
     * @memberof UserFullInfo
     */
    grade?: number;
    /**
     * 
     * @type {string}
     * @memberof UserFullInfo
     */
    status?: string;
    /**
     * 
     * @type {Array<Course>}
     * @memberof UserFullInfo
     */
    courses?: Array<Course>;
}

/**
 * Check if a given object implements the UserFullInfo interface.
 */
export function instanceOfUserFullInfo(value: object): value is UserFullInfo {
    return true;
}

export function UserFullInfoFromJSON(json: any): UserFullInfo {
    return UserFullInfoFromJSONTyped(json, false);
}

export function UserFullInfoFromJSONTyped(json: any, ignoreDiscriminator: boolean): UserFullInfo {
    if (json == null) {
        return json;
    }
    return {
        
        'name': json['name'] == null ? undefined : json['name'],
        'studentId': json['studentId'] == null ? undefined : json['studentId'],
        'email': json['email'] == null ? undefined : json['email'],
        'phone': json['phone'] == null ? undefined : json['phone'],
        'major': json['major'] == null ? undefined : json['major'],
        'grade': json['grade'] == null ? undefined : json['grade'],
        'status': json['status'] == null ? undefined : json['status'],
        'courses': json['courses'] == null ? undefined : ((json['courses'] as Array<any>).map(CourseFromJSON)),
    };
}

export function UserFullInfoToJSON(json: any): UserFullInfo {
    return UserFullInfoToJSONTyped(json, false);
}

export function UserFullInfoToJSONTyped(value?: UserFullInfo | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'name': value['name'],
        'studentId': value['studentId'],
        'email': value['email'],
        'phone': value['phone'],
        'major': value['major'],
        'grade': value['grade'],
        'status': value['status'],
        'courses': value['courses'] == null ? undefined : ((value['courses'] as Array<any>).map(CourseToJSON)),
    };
}

