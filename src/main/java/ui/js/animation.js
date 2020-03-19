$(document).ready(function() {
    getAWSData();
});

 function getAWSData() {
    //Create and config a new instance of AWS S3 Bucket
    AWS.config.region = 'eu-west-2';
    AWS.config.credentials = new AWS.CognitoIdentityCredentials({
        IdentityPoolId: 'eu-west-2:91485a9b-7736-4a9a-96bc-e24adef8d550',
    });

    window.s3 = new AWS.S3();

    //Name of bucket and the file to be retrieved
    var params = {
        Bucket: "dissertation-bucket",
        Key: "webDisplay.json"
    };

    //Callback function to ensure launchUI() only runs when S3 has returned the data
    getFile(params, function callUI() {
        launchUI();
    });
 }

function getFile(params, callback) {
    window.s3.getObject(params, function (error, data) {
        if (error) {
            console.log(error);
        } else {
            //data.body.toString() returns the raw data from getObject call
            const fileContents = data.Body.toString();
            window.split = fileContents.split("\n");
            callback();
        }
    });
}

/**
 * Generate animated graph using Cytoscape.js
 */
 function launchUI() {
     var options = {
        klay: {
            addUnnecessaryBendpoints: true,
            direction: 'VERTICAL',
            fixedAlignment: 'BALANCED'
        }
     };
     console.log(window.split[0]);
     console.log(window.split[1]);
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
            nodes: JSON.parse(window.split[0]),
            edges: JSON.parse(window.split[1])
        },

        layout: {
            name: 'breadthfirst'
        }
    });

    //When the user taps a given node, update the information displayed
    cy.on('tap', 'node', function(evt){
        var node = evt.target;
        updateDisplay(evt);
    });

    function updateDisplay(evt) {
        var source = (evt.target).id();
        //elements 0 and 1 are node/edge arrays and can be ignored
        for(i = 2; i < window.split.length; i++) {
            //Returns the id which represents the node the data is attached to
            var id = (Object.keys(JSON.parse(window.split[i])))[0];
            if(id === source) {
                formatData(JSON.parse(window.split[i]));
                break;
            }
        }
    }

    function formatData(data) {
        console.log(data);
    }
}