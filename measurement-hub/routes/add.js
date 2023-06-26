const express = require('express');
const router  = express.Router();

const openHab   = require('../controllers/openHabController.js');
const DataPoint = require('../models/dataPointModel.js');

router.post('/', addDataPoint);


function addDataPoint(req, res) {
    console.log(req.body);
    /*
    openHab.getItems()
        .then((items) => {
            let data = new DataPoint({
                timestamp: constructTimestamp(),
                value    : req.body.value,
                items    : items
            });

            data.save();
            res.status(200).send({ msg: "Saved data point" });

        });
    */
    res.status(200).send({ msg: "Saved data point" });
}

function constructTimestamp() {
    const now = new Date();

    const month = now.getMonth() + 1;
    const date  = now.getFullYear() + "-" + ("0" + month).slice(-2) + "-" + now.getDate();

    const hours   = now.getHours();
    const minutes = now.getMinutes();
    const seconds = now.getSeconds();
    const time = ("0" + hours).slice(-2) + ":" +
        ("0" + minutes).slice(-2) + ":" +
        ("0" + seconds).slice(-2)

    return date + " " + time;
}

module.exports = router;
