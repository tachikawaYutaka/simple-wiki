$(function(){
    $('.script-toggle-base-menu').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.base-menu').addClass('active');
            $('.base-body').addClass('overflow-hidden');
        }else {
            $(this).removeClass('active');
            $('.base-menu').removeClass('active');
            $('.base-body').removeClass('overflow-hidden');
            $('.module-back-layer').remove();
        }
    });

    $('.script-toggle-side-menu').on('click',function(e){
        if(!$(this).hasClass('active')) {
            var height = $('.base-content-body-head').outerHeight();
            $('.base-content-side').css('top',height + 60);
            $('.base-content-side').css('padding-bottom',height + 60 + 100);
            $('.base-body').append('<div class="module-back-layer"></div>');
            $(this).addClass('active');
            $('.base-content-side').addClass('active');
             $('.base-body').addClass('overflow-hidden');
        }else {
            $('.script-toggle-side-menu').removeClass('active');
            $('.base-body').removeClass('overflow-hidden');
            $('.base-content-side').removeClass('active');
            $('.module-back-layer').remove();
            setTimeout(function(){
                $('.base-content-side').css('top','auto');
                $('.base-content-side').css('padding-bottom',0);
            },100);
        }
    });

    $('.base-body').on('click','.module-back-layer',function(e){
        e.preventDefault();
        $('.script-toggle-base-menu').removeClass('active');
        $('.base-menu').removeClass('active');
        $('.base-body').removeClass('overflow-hidden');

        $('.script-toggle-side-menu').removeClass('active');
        $('.base-content-side').removeClass('active');
        $('.module-back-layer').remove();

        setTimeout(function(){
            $('.base-content-side').css('top','auto');
            $('.base-content-side').css('padding-bottom',0);
        },100);
    });
});