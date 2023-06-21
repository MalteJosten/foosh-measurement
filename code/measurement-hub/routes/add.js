const express = require('express');
const router  = express.Router();

const openHab   = require('../controllers/openHabController.js');
const DataPoint = require('../models/dataPointModel.js');

router.post('/', addDataPoint);


function addDataPoint(req, res) {
    console.log("Request body: ", req.body);

   
    openHab.getItems()
        .then((items) => {
            let data = new DataPoint({
                timestamp: req.body.timestamp,
                value    : req.body.value,
                items    : items
            });

            data.save();

            res.status(200).send({ msg: "Saved data point" });
        });
}

module.exports = router;
