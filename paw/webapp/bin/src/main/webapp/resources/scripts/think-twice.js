define(['swal', 'jquery'], function(swal, $) {
    var alertPrompt = {
        title: "Are you sure?",
        text: "You will not be able to recover this!",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel :(",
        closeOnConfirm: false
    };
    
    return function(selector) {
        selector = selector || '[data-think="twice"]';
        
        $(selector).on('submit', function thinkTwice(event) {
            event.stopPropagation();
            event.stopImmediatePropagation();
            event.preventDefault();
            function onAnswerHandler (isConfirm){
                if (isConfirm) {
                    $(event.target).off('submit', thinkTwice);
                    $(event.target).submit();
                    swal("Deleted!", "Your thing has been deleted.", "success");
                    return true;
                }
            }
            
            swal(alertPrompt, onAnswerHandler);
        });
    };
});