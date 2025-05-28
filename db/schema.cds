namespace com.sap.gyo;

using { managed } from '@sap/cds/common';

entity User : managed {
    key userId : UUID;
    name : String;
    lastName : String;
    empID : String(6);
    approver : String(6);
    loginId : String;
}