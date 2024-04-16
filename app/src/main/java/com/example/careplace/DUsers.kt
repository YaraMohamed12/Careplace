package com.example.careplace

class DUsers {
    var DName: String? = null
    var DEmail: String? = null
    var DUID: String? = null
    var DPhone :String? =null
    var Dnationid : String? = null
    var DBdate : String ?= null
    var DAge : String ? = null
    var DGender : String ? = null
    var Specialization : String ? = null
    constructor()

    constructor(Dname: String?, Demail: String?, Duid: String? ,
                DPhone :String?, Dnationid : String ?,DBdate : String?, DAge : String?,
                DGender : String?,Dspeciliziton : String? ) {
        this.DName = Dname
        this.DEmail = Demail
        this.DUID = Duid
        this.DBdate = DBdate
        this.DAge = DAge
        this.DPhone = DPhone
        this.DGender = DGender
        this.Dnationid = Dnationid
        this.Specialization = Dspeciliziton

    }
}