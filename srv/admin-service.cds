using { com.sap.gyo as db } from '../db/schema';

service AdminService {
    entity Products   as projection on db.Products;
    entity Categories as projection on db.Categories;
}