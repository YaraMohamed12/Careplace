package com.example.careplace

class Users {
    var Name: String? = null
    var Email: String? = null
    var UID: String? = null
    var Phone :String? =null
    var nationid : String? = null
    var lenght : String ? = null
    var wieght : String ? = null
    var Bdate : String ?= null
    var Age : String ? = null
    var Gender : String ? = null


    constructor()

    constructor(name: String?, email: String?, uid: String? ,
                Phone :String?, nationid : String ?, lenght : String? ,
                wieght : String?, Bdate : String?, Age : String?,
                Gender : String? ) {
        this.Name = name
        this.Email = email
        this.UID = uid
        this.Bdate = Bdate
        this.Age = Age
        this.Phone = Phone
        this.lenght = lenght
        this.wieght = wieght
        this.Gender = Gender
        this.nationid = nationid

    }


}