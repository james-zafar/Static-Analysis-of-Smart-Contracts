/**
 * This file is used to add static styles and tool tip text to the help elements present in the content area
 */

//Define hover actions for each of the default elements
$("#smartContract").hover(function () {
    changeStyle("#smartContract");
    }, function () {
    revertDefaultStyle("#smartContract");
});

$("#opcodes").hover(function () {
    changeStyle("#opcodes");
}, function () {
    revertDefaultStyle("#opcodes");
});

$("#howToUse").hover(function () {
    changeStyle("#howToUse");
}, function () {
    revertDefaultStyle("#howToUse");
});

//Add qTip tooltip to each of the 3 helper functions
addText("#smartContract", "#hiddenText1");
addText("#opcodes", "#hiddenText2");
addText("#howToUse", "#hiddenText3");

/**
 *
 * @param target the element the tooltip is being added to
 * @param textSource the html element with the text to be displayed within the tooltip
 */
function addText(target, textSource) {
    $(target).qtip({
        content: {
            text: $(textSource)
        },
        show: {
            event: 'mouseover'
        },
        position: {
            my: 'top left',
            at: 'bottom left',
            target: $(target)
        },
        adjust: {
            mouse: true,
            resize: true
        },
        style: {
            classes: 'qtip-dark qtip-youtube qtip-rounded',
            tip: 'topLeft'
        },
        hide: {
            fixed: true,
            delay: 25
        }
    })
        .attr('title', 'Help');
}

/**
 *
 * @param source the name of the source element
 */
function revertDefaultStyle(source) {
    //When not hovering, revert background to original
    $(source).css({
        "background": "#ffdd99"
    });
}