jQuery(function ($) {
  // Dropdown menu
  $('.sidebar-dropdown > a').click(function () {
    $('.sidebar-submenu').slideUp(200);
    if ($(this).parent().hasClass('active')) {
      $('.sidebar-dropdown').removeClass('active');
      $(this).parent().removeClass('active');
    } else {
      $('.sidebar-dropdown').removeClass('active');
      $('.sidebar-submenu2').slideUp(200);
      $(this).next('.sidebar-submenu').slideDown(200);
      $(this).parent().addClass('active');
    }
  });
   $('.sidebar-dropdown2 > a').click(function () {
    $('.sidebar-submenu2').slideUp(200);
    if ($(this).parent().hasClass('active')) {
      $('.sidebar-dropdown2').removeClass('active');
      $(this).parent().removeClass('active');
    } else {
      $('.sidebar-dropdown2').removeClass('active');
      $(this).next('.sidebar-submenu2').slideDown(200);
      $(this).parent().addClass('active');
    }
  });

  //toggle sidebar
  $('#toggle-sidebar').click(function () {
    $('.page-wrapper').toggleClass('toggled');
  });
$('.page-wrapper').toggleClass('toggled');
  // bind hover if pinned is initially enabled
  if ($('.page-wrapper').hasClass('pinned')) {
    $('#sidebar').hover(
      function () {
        console.log('mouseenter');
        $('.page-wrapper').addClass('sidebar-hovered');
      },
      function () {
        console.log('mouseout');
        $('.page-wrapper').removeClass('sidebar-hovered');
      }
    );
  }

  //Pin sidebar
  $('#pin-sidebar').click(function () {
    if ($('.page-wrapper').hasClass('pinned')) {
      // unpin sidebar when hovered
      $('.page-wrapper').removeClass('pinned');
      $('#sidebar').unbind('hover');
    } else {
      $('.page-wrapper').addClass('pinned');
      $('#sidebar').hover(
        function () {
          console.log('mouseenter');
          $('.page-wrapper').addClass('sidebar-hovered');
        },
        function () {
          console.log('mouseout');
          $('.page-wrapper').removeClass('sidebar-hovered');
        }
      );
    }
  });
$('.page-wrapper').toggleClass('toggled');
  //toggle sidebar overlay
  $('#overlay').click(function () {
    $('.page-wrapper').toggleClass('toggled');
  });

  //switch between themes
  var themes =
    'default-theme legacy-theme chiller-theme ice-theme cool-theme light-theme';
  $('[data-theme]').click(function () {
    $('[data-theme]').removeClass('selected');
    $(this).addClass('selected');
    $('.page-wrapper').removeClass(themes);
    $('.page-wrapper').addClass($(this).attr('data-theme'));
  });

  // switch between background images
  var bgs = 'bg1 bg2 bg3 bg4';
  $('[data-bg]').click(function () {
    $('[data-bg]').removeClass('selected');
    $(this).addClass('selected');
    $('.page-wrapper').removeClass(bgs);
    $('.page-wrapper').addClass($(this).attr('data-bg'));
  });

  // toggle background image
  $('#toggle-bg').change(function (e) {
    e.preventDefault();
    $('.page-wrapper').toggleClass('sidebar-bg');
  });

  // toggle border radius
  $('#toggle-border-radius').change(function (e) {
    e.preventDefault();
    $('.page-wrapper').toggleClass('boder-radius-on');
  });

  //custom scroll bar is only used on desktop
  if (
    !/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent
    )
  ) {
    $('.sidebar-content').mCustomScrollbar({
      axis: 'y',
      autoHideScrollbar: true,
      scrollInertia: 300,
    });
    $('.sidebar-content').addClass('desktop');
  }
});
function printContent(el){
  
	 var divToPrint=document.getElementById(el);

	  var newWin=window.open('','Print-Window');

	  newWin.document.open();

	  newWin.document.write('<html> <link rel="stylesheet"href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"crossorigin="anonymous">');
	  newWin.document.write('<style> *{font-size: 16px;} .fig1{  height: 60%!important} .dt-button{display:none!important;} #table1{border: 1px solid black; width: 80%!important; height: 70%!important;} </style>'); 
	  newWin.document.write('<body onload="window.print()"> ');
	  
	  
	  
	  newWin.document.write(divToPrint.innerHTML);
	  
	  newWin.document.write('</body></html>');

	  newWin.document.close();

	  setTimeout(function(){newWin.close();},10);
}
