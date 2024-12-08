/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Course } from './Course';

export type CoursePlan = {
    id?: string;
    semesterNumber?: number;
    courses?: Array<Course>;
}
