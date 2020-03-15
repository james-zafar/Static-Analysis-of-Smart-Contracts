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
             callback(error);
         } else {
             const fileContents = data.Body.toString();
             window.split = fileContents.split("\n");
             callback();
         }
     });
 }

/**
 * Generate animated display using Cytoscape.js
 */
 function launchUI() {
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
            nodes: window.split[0],
            edges: window.split[1]
            /*
            nodes: [
                {data: {id: 'Branch1'}},
                {data: {id: 'Branch2'}},
                {data: {id: 'Branch3'}},
                {data: {id: 'Branch4'}}
            ],
            edges: [
                {data: {id: 'b12', source: 'Branch1', target: 'Branch2'}},
                {data: {id: 'b13', source: 'Branch1', target: 'Branch3'}},
                {data: {id: 'b23', source: 'Branch2', target: 'Branch3'}},
                {data: {id: 'b24', source: 'Branch2', target: 'Branch4'}},
                {data: {id: 'b14', source: 'Branch1', target: 'Branch4'}}
            ]
             */
        },

        layout: {
            name: 'breadthfirst'
        }
    });

    /*var a = cy.getElementById('Branch1');
    var b = cy.getElementById('Branch2');
    var ab = cy.getElementById('b23');
    var c = cy.getElementById('Branch3');
    var d = cy.getElementById('Branch 4');

    var makeDiv = function (text) {
        var div = document.createElement('div');

        div.classList.add('popper-div');

        div.innerHTML = text;

        document.body.appendChild(div);

        return div;
    };

    var popperA = a.popper({
        content: function () {
            return makeDiv('Additional Info');
        }
    });

    var updateA = function () {
        popperA.scheduleUpdate();
    };

    a.on('position', updateA);
    cy.on('pan zoom resize', updateA);


    var popperAB = ab.popper({
        content: function () {
            return makeDiv('Branch not executed');
        }
    });

    var updateAB = function () {
        popperAB.scheduleUpdate();
    };

    ab.connectedNodes().on('position', updateAB);
    cy.on('pan zoom resize', updateAB);*/
}