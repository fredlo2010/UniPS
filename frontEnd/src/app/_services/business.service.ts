import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http'
import {Observable} from 'rxjs/Rx'
import { BusinessEntity } from '../_models/businessEntity.model';
import { Address } from '../_models/address.model';
import { Comment } from '../_models/comment.model';

//  Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class BusinessService {
    constructor(private _http: Http) { }

    private businessUserToRedirectTo:string;

    private businessUrl = '/api/business'; // URL to web api

    getBusinesses() {
        return this._http.get(this.businessUrl)
                        .map((res:Response) => res.json());
  }

    getBusiness(username:string) {
        var requestUrl:string = this.businessUrl + "/" + username;
        return this._http.get(requestUrl)
                        .map((res:Response) => res.json());
    }

    setBusinessUserToRedirectTo(username: string){
        this.businessUserToRedirectTo = username;
    }

    getBusinessUserToRedirectTo(): string {
        return this.businessUserToRedirectTo;
    }
}
