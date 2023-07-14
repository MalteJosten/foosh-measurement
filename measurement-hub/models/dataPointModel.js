const mongoose = require('mongoose');
const envy = require('envy');
const env  = envy();

const DataPointSchema = new mongoose.Schema({
    timestamp: { type: Date, required: true },
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
    },{
        collection: env.db_name
    }
);


module.exports = mongoose.model('DataPoint', DataPointSchema);
