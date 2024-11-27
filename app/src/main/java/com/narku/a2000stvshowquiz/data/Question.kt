package com.narku.a2000stvshowquiz.data

class Question(
    var title: String,
    var network: String,
    var yearsAired: String,
    var seasonNum: Int,
    var synopsis: String,
    var vidName: String
) {

    override fun toString(): String {
        return "Question(title='$title', network='$network', yearsAired=$yearsAired, seasonNum=$seasonNum, synopsis='$synopsis', vidName='$vidName')"
    }

}
