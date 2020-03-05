document.addEventListener('DOMContentLoaded', function () {
    $(document).ready(function() {
    $.getJSON("./../js/webDisplay.json", function (data) {
        console.log("Here");
    });
});

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
        },

        layout: {
            name: 'breadthfirst'
        }
    });

    var a = cy.getElementById('Branch1');
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
    cy.on('pan zoom resize', updateAB);

});
