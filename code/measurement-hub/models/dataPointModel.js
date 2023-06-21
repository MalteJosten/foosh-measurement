const mongoose = require('mongoose');

const DataPointSchema = new mongoose.Schema({
    timestamp: { type: Date,   default: Date.now() },
    value    : { type: Number, required: true },
    items    : [{
                link: {
                    type: String,
                    required: true
                },
                state: {
                    type: String,
                    required: true
                },
                editable: {
                    type: Boolean,
                    required: true
                },
                type: {
                    type: String,
                    required: true
                },
                name: {
                    type: String,
                    required: true
                },
                label: {
                    type: String,
                    required: true,
                },
                category: {
                    type: String,
                    required: true
                },
                tags: [{
                    type: String
                }],
                groupNames: [{
                    type: String
                }]
    }]
});

module.exports = mongoose.model('DataPoint', DataPointSchema);
