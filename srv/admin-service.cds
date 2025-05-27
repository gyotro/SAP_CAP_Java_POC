using { com.sap.gyo as db } from '../db/schema';

service AdminUser {
    entity User   as projection on db.User;
}