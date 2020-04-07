$("#smartContract").hover(function () {
    changeStyle("#smartContract");
    }, function () {
    revertStyles("#smartContract");
});

$("#opcodes").hover(function () {
    changeStyle("#opcodes");
}, function () {
    revertStyles("#opcodes");
});

$("#howToUse").hover(function () {
    changeStyle("#howToUse");
}, function () {
    revertStyles("#howToUse");
});

addText("#smartContract", "#hiddenText1");
addText("#opcodes", "#hiddenText2");
addText("#howToUse", "#hiddenText3");

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
        }
    })
        .attr('title', 'Help');
}