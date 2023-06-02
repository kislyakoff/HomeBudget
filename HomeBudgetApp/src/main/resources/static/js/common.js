function isEmpty(value) {
	return !value || !value.length || /^\s+$/.test(value);
}

function preloader(active) {
	if (active) {
		$('.preloader').removeClass('d-none');
	} else {
		$('.preloader').addClass('d-none');
	}
}

const dateNow = new Date().toLocaleDateString("ru-RU");

let getNbRates = () => {
	$('#curr-rate').append(' ' + dateNow + ':');
	$.ajax({
		url: `/homebudget/rates/nbrb`,
		type: 'GET',
		dataType: "json",
		success: (data) => {
			const nb = $('#nb-rate');
			$.each(data, function(key, value) {

				switch (value.cur_Abbreviation) {
					case "USD":
						$('<tr><td>USD:</td><td>' + value.cur_OfficialRate + '</td></tr>').appendTo(nb);
						break;
					case "EUR":
						$('<tr><td>EUR:</td><td>' + value.cur_OfficialRate + '</td></tr>').appendTo(nb);
						break;
					case "RUB":
						$('<tr><td>100RUB:</td><td>' + value.cur_OfficialRate + '</td></tr>').appendTo(nb);
						break;
				}
			});
		}
	});
};

let getCbRates = () => {
	$('#curr-rate-cbr').append(' ' + dateNow + ':');
	$.ajax({
		url: `/homebudget/rates/cbr`,
		type: 'GET',
		dataType: "json",
		success: (data) => {
			const cb = $('#cb-rate');
			$.each(data, function(key, value) {

				switch (value.charCode) {
					case "USD":
						$('<tr><td>USD:</td><td>' + value.value + '</td></tr>').appendTo(cb);
						break;
					case "EUR":
						$('<tr><td>EUR:</td><td>' + value.value + '</td></tr>').appendTo(cb);
						break;
				}
			});
		}
	});
}

const seekParam = '?seek=';