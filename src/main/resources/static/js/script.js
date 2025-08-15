(function ($) {
	"use strict";

    jQuery(document).ready(function ($) {
        
        $(".thumb-image").find('img').bind("click", function() {
            var src = $(this).attr("src");
            // Check the beginning of the src attribute  
            var state = (src.indexOf("bw_") === 0) ? 'bw' : 'clr';
            // Modify the src attribute based upon the state var we just set
            (state === 'bw') ? src = src.replace('bw_', 'clr_') : src = src.replace('clr_', 'bw_');
            // Apply the new src attribute value  
            $(this).attr("src", src);

            // This is just for demo visibility
            $('.thumb-main-image img').attr("src", src);
            
            $('.thumb-image li.active').removeClass('active');
            
            $(this).parent().parent().addClass('active');
            
            

          return false;
        });
        
        var spins = document.getElementsByClassName("qt-area");
        for (var i = 0, len = spins.length; i < len; i++) {
            var spin = spins[i],
                span = spin.getElementsByTagName("i"),
                input = spin.getElementsByTagName("input")[0];

            input.onchange = function() { input.value = +input.value || 0; };
            span[0].onclick = function() { input.value = Math.max(0, input.value - 1); };
            span[1].onclick = function() { input.value -= -1; };
        }

        // 장바구니 담기 버튼 클릭 시 AJAX로 백엔드 호출
        $(document).on('click', '.cart-add-btn', function() {
            var productId = $(this).data('product-id');
            var productName = $(this).data('product-name');
            var productPrice = $(this).data('product-price');
            $.ajax({
                url: '/cart/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ productId: productId, quantity: 1 }),
                success: function() {
                    // 장바구니 정보 새로고침
                    updateCartSummary();
                },
                error: function(xhr) {
                    alert('장바구니 담기 실패: ' + (xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : '로그인 필요 또는 서버 오류'));
                }
            });
        });

        // 장바구니 개수/금액 요약 정보 갱신
        function updateCartSummary() {
            $.ajax({
                url: '/cart/list',
                type: 'GET',
                success: function(cartList) {
                    var totalCount = 0;
                    var totalPrice = 0;
                    for (var i = 0; i < cartList.length; i++) {
                        var qty = parseInt(cartList[i].quantity) || 0;
                        var price = parseInt(cartList[i].price) || 0;
                        totalCount += qty;
                        totalPrice += price * qty;
                    }
                    $(".cart-text").html('장바구니<br>' + totalCount + ' 건 - ' + totalPrice.toLocaleString() + '원');
                },
                error: function() {
                    $(".cart-text").html('장바구니<br>0 건 - 0원');
                }
            });
        }

        // 페이지 로드 시 장바구니 정보 갱신
        updateCartSummary();


    });


    jQuery(window).load(function(){

        
    });


}(jQuery));