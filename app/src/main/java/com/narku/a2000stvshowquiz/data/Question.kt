package com.narku.a2000stvshowquiz.data

class Question(
    var title: String,
    var network: String,
    var yearAired: Int,
    var seasonNum: Int,
    var synopsis: String,
    var vidName: String
) {

    override fun toString(): String {
        return "Question(title='$title', network='$network', yearAired=$yearAired, seasonNum=$seasonNum, synopsis='$synopsis', vidName='$vidName')"
    }

}
