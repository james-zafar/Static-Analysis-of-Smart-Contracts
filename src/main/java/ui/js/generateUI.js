$(document).ready(function() {
    getAWSData();
});

function getAWSData() {
    //Create and config a new instance of AWS S3 Bucket
    AWS.config.region = 'eu-west-2';
    AWS.config.credentials = new AWS.CognitoIdentityCredentials({
        IdentityPoolId: 'eu-west-2:91485a9b-7736-4a9a-96bc-e24adef8d550',
    });

    var s3 = window.s3 = new AWS.S3();

    //Name of bucket and the file to be retrieved
    var params = {
        Bucket: "dissertation-bucket",
        Key: "webDisplay.json"
    };

    //Bucket and object to use
    var dictParams = {
        Bucket: "dissertation-bucket",
        Key: "Dictionary.json"
    };

    //Callback function to ensure launchUI() only runs when S3 has returned the data
    getFile(params, function callUI() {
        launchUI();
        var dictionaryManager = window.dictionaryManager = $.Deferred();
        getDictionary(dictParams);
    });
}

/**
 *
 * @param params the name of the bucket and the object to be retrieved
 * @param callback a function to be executed once the object has been retrieved
 */
function getFile(params, callback) {
    window.s3.getObject(params, function (error, data) {
        if (error) {
            console.log(error);
        } else {
            //data.body.toString() returns the raw data from getObject call
            const fileContents = data.Body.toString();
            var split = window.split = fileContents.split("\n");
            callback();
        }
    });
}

/**
 * Generate animated graph using Cytoscape.js
 */
function launchUI() {
    var cy = window.cy = cytoscape({
        container: document.getElementById('cy'),
        style: [
            {
                selector: 'node',
                style: {
                    'background-color': '#99ccff',
                    'label': 'data(id)'
                }
            },

            {
                selector: 'edge',
                style: {
                    'curve-style': 'bezier',
                    'target-arrow-shape': 'triangle',
                    'line-color': '#99ccff',
                    'target-arrow-color': '#99ccff',
                }
            }
        ],

        elements: {
            nodes: JSON.parse(window.split[0]),
            edges: JSON.parse(window.split[1])
        },

        layout: {
            name: 'klay',
            fit: true,
            animate: true,
            feedbackEdges: true,
            fixedAlignment: 'RIGHTDOWN',
            nodePlacement: 'LINEAR_SEGMENTS',
            thoroughness: 10
        }
    });
    //When the user taps a given node, update the information displayed
    cy.on('tap', 'node', function(evt){
        updateDisplay(evt);
    });

}

/**
 *
 * @param evt the event generated from hovering over the target
 */
function updateDisplay(evt) {
    const source = (evt.target).id();
    //elements 0 and 1 are node/edge arrays and can be ignored
    for(let i = 2; i < window.split.length; i++) {
        //Returns the id which represents the node the data is attached to
        var id = (Object.keys(JSON.parse(window.split[i])))[0].replace(/\D/g,'');
        if(id === source) {
            formatData(JSON.parse(window.split[i]), id, function callUpdate() {
                updateContentArea();
            });
            break;
        }
    }
}

/**
 *
 * @param data All branches in raw JSON format
 * @param id the branch number
 * @param callback function to update the content area upon finishing
 */
function formatData(data, id, callback) {
    const temp = "Branch" + id;
    //Gets each element of original JSON data
    window.dataLines = data[temp].split(", ");
    if(JSON.stringify(window.dataLines[0]).includes("Branches")) {
        addBranchTitle(id, window.dataLines[0]);
        //Remove first array element as no longer needed
        window.dataLines.splice(0, 1);
    }else {
        addBranchTitle(id);
    }
    //Updates the area showing the branch detail
    callback();
}

/**
 *
 * @param id the branch number to be added
 * @param branches the branch(es) to be included in the title
 */
function addBranchTitle(id, branches) {
    //Branches optional parameter, only present if node represents many branches
    if(branches === undefined) {
        $("#branchArea").text("Branch: " + id);
    }else {
        //Remove identifier text before displaying
        var newTitle = branches.replace('[Branches <', '');
        newTitle = newTitle.replace('>', '');
        newTitle = newTitle.split(' ').join(', ');
        var display = id + ", " + newTitle;
        $("#branchArea").text("Branches: " + display);
    }
}

function updateContentArea() {
    //Clear old display
    $("div.contentHolder").empty();
    $("#contentArea div").empty().removeAttr("style");
    for(let i = 0; i < window.split.length; i++) {
        window.helpTextManager = $.Deferred();
        //Root is an identifier with no data
        if(window.dataLines[i] === '[ROOT') {
            i++;
        }
        const classID = "element" + i;
        const toolTextID = "tooltip" + i;
        //Create a hidden div with relevant tooltip and then add new tooltip
        createNewDiv(classID, toolTextID, i);
        getHelpText(classID);
        (function() {
            $.when(window.helpTextManager).done(function() {
                //Create div only when help text has been assigned
                createHiddenDiv(i, toolTextID);
            });
        })();
        addToolTip(classID, toolTextID);
    }
}


/**
 *
 * @param classID the class to be assigned to the dib
 * @param toolTextID the id to be assigned to the hidden div
 * @param count the current number to be associated with the div
 */
function createNewDiv(classID, toolTextID, count) {
    $( "<div />", {
        "class": classID,
        "id": classID,
        text: window.dataLines[count],
        on: {
            mouseover: function() {
                changeStyle("#" + this.id, count);
            },
            mouseleave: function() {
                revertStyles("#" + this.id);
            },
        },
        css: {
            paddingBottom: "10px",
            width: "100%",
            paddingTop: "10px",
            paddingLeft: "5px",
            background: "#ffad33",
        }
    })
        .appendTo("div.contentHolder");

    //Get the help text for the div and then create new hidden div
}

/**
 *
 * @param count the element number associated with the parent div
 * @param toolTextID the class and id to be associated with hidden div
 */
function createHiddenDiv(count, toolTextID) {
    //Hidden div inside of element with tooltip info
    $( "<div />", {
        "class": toolTextID,
        "id": toolTextID,
        //Get text from dictionary
        text: window.finished,
        css: {
            display: "none"
        }
    })
        .appendTo("#element" + count);
}

/**
 *
 * @param classID the class of the div the tooltip is to be added to
 * @param hiddenDivID the class of the div containing the text to be added to tooltip
 */
function addToolTip(classID, hiddenDivID) {
    //Create a tooltip for each line of output
    $("#" + classID).qtip({
        content: {
            //Get text content from hidden div
            text: $("#" + hiddenDivID)
        },
        show: {
            event: 'mouseover'
        },
        effect: {
            function(pos) {
                $(this).animate(pos, {
                    duration: 1000,
                    queue: false
                });
            }
        },
        position: {
            my: 'top left',
            at: 'bottom left',
            target: $('#' + classID)
        },
        adjust: {
            mouse: true,
            resize: true
        },
        style: {
            classes: 'qtip-dark qtip-youtube qtip-rounded',
            tip: 'topLeft'
        }
    })
        .attr('title', 'Help');
}

/**
 *
 * @param source the name of the source element
 */
function changeStyle(source) {
    //on hover change the background color
    $(source).css({
        "background": "#ffc266"
        });
}

/**
 *
 * @param source the name of the source element
 */
function revertStyles(source) {
    //When not hovering, revert background to original
    $(source).css({
        "background": "#ffad33"
    });
}


/**
 *
 * @param source the name of the element text is required for
 */
function getHelpText(source) {
    //Only execute when dictionary has been created
    $.when(window.dictionaryManager).done(function() {
        var finished = window.finished = findDefinition(source);
        //Confirm the definition has been found
        window.helpTextManager.resolve();
    });
}

/**
 *
 * @param params the name of the bucket and the object to be retrieved
 */
function getDictionary(params) {
    window.s3.getObject(params, function (error, data) {
        if (error) {
            console.log(error);
        } else {
            //data.body.toString() returns the raw data from getObject call
            const fileContents = data.Body.toString();
            window.dictionary = JSON.parse(fileContents);
            //console.log(Object.keys((JSON.parse(window.dictionary[10]))));
        }
    });
    window.dictionaryManager.resolve();
}

/**
 *
 * @param source the name of the element a definition is required for
 * @returns {string|*} the tool tip text
 */
function findDefinition(source) {
    var text = $('#' + source).text();
    //Return the object with the correct definition
    var searchFor = (text.split(' '))[1];
    //As array indexes do not match actual opcode ref, search based on opcode ref
    for(let i = 0; i < window.dictionary.length; i++) {
        if(window.dictionary[i].id === searchFor) {
           var returnVal = ((window.dictionary[i].value));
        }
    }
    //Undefined if no help available for given opcode
    if(returnVal === undefined) {
        return "No suggestions available";
    }else {
        return returnVal;
    }
}