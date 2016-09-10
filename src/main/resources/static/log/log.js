/**
 * Created by Alan on 2016/9/7.
 */
var futuresID = getParam(location.href, "futures_id");
var getHistoryUrl = "/api/getHistory";

//loadResource();

function loadResource() {
    $.ajax({
        url: window.host + getHistoryUrl,
        data: {
            futures_id: futuresID
        },
        timeout: 5000,
        success: function (res) {
            var data = response(res);
            if (data != null) {
                var quantity = data.quantity;
                var averagePrice = data.average_price;
                var historyList = data.data;
                for (var i = 0; i < historyList.length; i++) {
                    var date = historyList[i].date;
                    var price = historyList[i].price;
                    var historyQuantity = historyList[i].quantity;
                    loadHistory(date, price, historyQuantity);
                }
            }
        }
    });
}

var history_template =
    '<tr> ' +
    '<td>date</td> ' +
    '<td>price</td> ' +
    '<td>quantity</td> ' +
    '</tr>';

/**
 * 加载历史信息
 * @param date
 * @param price
 * @param quantity
 */
function loadHistory(date, price, quantity) {
    var tags = ['date', 'price', 'quantity'];
    var data = [date, price, quantity];
    var template = replaceTemplate(history_template, tags, data);

    $('#data-table').append(template);
}