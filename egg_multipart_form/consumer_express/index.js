const express = require('express');
const multer = require('multer');
const upload = multer({ dest: 'uploads/' })
const app =express()

app.post('/consume',upload.none(),(req,res)=>{
    console.log(req.body)
    res.status(200).json("ok")
})

app.listen(5150)
