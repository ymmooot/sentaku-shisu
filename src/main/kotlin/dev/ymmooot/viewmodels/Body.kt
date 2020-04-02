package dev.ymmooot.viewmodels

import com.fasterxml.jackson.annotation.JsonIgnore

class Body(@JsonIgnore val areaName: String, val attachments: List<Attachment>) {
    val username = "洗濯指数お知らせくん"
    val iconUrl = "https://1.bp.blogspot.com/-lYIO17nZ46g/UO1DreRVBdI/AAAAAAAAKfY/QYDHThbdH_M/s400/sentaku_tshirt.png"
    val text = "${areaName}の洗濯指数をお知らせするよ"
}
