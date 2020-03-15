$(document).ready(function() {
    getAWSData();
});

 function getAWSData() {
     var def = new jQuery.Deferred();
     AWS.config.region = 'eu-west-2'; // Region
     AWS.config.credentials = new AWS.CognitoIdentityCredentials({
        IdentityPoolId: 'eu-west-2:91485a9b-7736-4a9a-96bc-e24adef8d550',
     });

     window.s3 = new AWS.S3();

    var params = {
        Bucket: "dissertation-bucket",
        Key: "webDisplay.json"
    };
    getFile(params, function callUI() {
        launchUI();
    });
 }

 function getFile(params, callback) {
     window.s3.getObject(params, function (error, data) {
         if (error) {
             console.log(error);
         } else {
             const fileContents = data.Body.toString();
             var split = fileContents.split("\n");
             window.inputNodes = split[0];
             window.inputEdges = split[1];
             console.log(window.inputNodes);
             console.log(window.inputEdges);
         }
     });
     callback();
 }

/**
 * Generate animated display using Cytoscape.js
 */
 function launchUI() {
    console.log(window.inputNodes);
    console.log(window.inputEdges);
    var cy = window.cy = cytoscape({
        container: document.getElementById('cy'),

        style: [
            {
                selector: 'node',
                style: {
                    'content': 'data(id)'
                }
            },

            {
                selector: 'edge',
                style: {
                    'curve-style': 'bezier',
                    'target-arrow-shape': 'triangle'
                }
            }
        ],

        elements: {
            nodes: window.inputNodes,
            edges: window.inputEdges
        },

        layout: {
            name: 'breadthfirst'
        }
    });
}