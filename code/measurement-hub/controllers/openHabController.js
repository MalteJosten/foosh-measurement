async function getItems() {
    let relevantItems = [];

    return new Promise((resolve, reject) => {
        fetch("http://192.168.108.103:8080/rest/items")
            .then((response) => {
                response.json()
                    .then((jsonData) => {
                        jsonData.forEach((element) => {
                            if (element.state != 'NULL') {
                                relevantItems.push(element);
                            }
                        });

                        resolve(relevantItems);

                    })
            })
            .catch (console.error);
    });
}

module.exports = { getItems }
