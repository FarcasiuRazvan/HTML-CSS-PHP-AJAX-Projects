//code for BOOK TABLE
$("#createBook").on("submit",function(){
    console.log("button clicked");
    var that = $(this),
        url = "api/create.php",
        type = "POST",
        data = {};
    that.find("[name]").each(function(index, value) {
        var that = $(this),
            name = that.attr("name"),
            value = that.val();
        data[name] = value;
    });
    $.ajax({
        url: url,
        type: type,
        data: JSON.stringify(data)
    }).done(function(result){
        getBooks();
    });
    return false;
});

$("#showBooks").on("click",function(){
    getBooks();
});

function getBooks() {
    $.ajax({
        url: "api/getAll.php",
        type: "GET"
    }).done(function(result){
        drawTable(result);
    });
}


function cleanTable() {
    $(`#bookData`)
        .find("tr:gt(0)")
        .remove();
}

function drawTable(data) {
    cleanTable();
    for (var i in data) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var row = $("<tr />");
    $(`#bookData`).append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
    row.append($("<td>" + rowData.id + "</td>"));
    row.append($("<td>" + rowData.title + "</td>"));
    row.append($("<td>" + rowData.author + "</td>"));
    row.append($("<td>" + rowData.pages + "</td>"));
    row.append($("<td>" + rowData.genre + "</td>"));
}

//code for LENDINGS TABLE
$("#showLendings").on("click",function(){
    getLendings();
});

$("#createLending").on("submit",function(){
    console.log("button clicked");
    var that = $(this),
        url = "api/createNewLending.php",
        type = "POST",
        data = {};
    that.find("[name]").each(function(index, value) {
        var that = $(this),
            name = that.attr("name"),
            value = that.val();
        data[name] = value;
    });
    $.ajax({
        url: url,
        type: type,
        data: JSON.stringify(data)
    }).done(function(result){
        getLendings();
    });
    return false;
});

function getLendings() {
    $.ajax({
        url: "api/getLendings.php",
        type: "GET"
    }).done(function(result){
        drawTableLendings(result);
    });
}


function cleanTableLendings() {
    $(`#lendingData`)
        .find("tr:gt(0)")
        .remove();
}

function drawTableLendings(data) {
    cleanTableLendings();
    for (var i in data) {
        drawRowLendings(data[i]);
    }
}

function drawRowLendings(rowData) {
    var row = $("<tr />");
    $(`#lendingData`).append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
    row.append($("<td>" + rowData.id + "</td>"));
    row.append($("<td>" + rowData.bookID + "</td>"));
    row.append($("<td>" + rowData.lendingDate + "</td>"));
}

//code for operations on books

//code for filter books
document.getElementById("filterBooks").addEventListener("click", function() {
    filterGenre();
})

function filterGenre() {
    var input = document.getElementById("showBooksBy2").value;
    // console.log(input);
    getBooksByGenre(input);
}

function getBooksByGenre(genre) {
    console.log("start request");
    $.ajax({
        url: "api/getAllByGenre.php?genre="+genre,
        type: "GET",
        success: function(result) {
        //console.log("done getall " + result);
        drawTable2(result);
        },
    error: function(result) {
        //console.log("done getall " + result);
        drawTable2(result);
    }});
}

function cleanTable2() {
$(`#bookData2`)
    .find("tr:gt(0)")
    .remove();
}

function drawTable2(data) {
cleanTable2();
for (var i in data) {
    drawRow2(data[i]);
}
}

function drawRow2(rowData) {
var row = $("<tr />");
$(`#bookData2`).append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
row.append($("<td>" + rowData.id + "</td>"));
row.append($("<td>" + rowData.title + "</td>"));
row.append($("<td>" + rowData.author + "</td>"));
row.append($("<td>" + rowData.pages + "</td>"));
row.append($("<td>" + rowData.genre + "</td>"));
}  

//code for deleting books
document.getElementById("deleteBooks").addEventListener("click", function() {
    deleteBook();
})

function deleteBook() {
    var input = document.getElementById("id").value;
    // console.log(input);
    deleteBooks(input);
}

function deleteBooks(id) {
    console.log("start request");
    $.ajax({
        url: "api/delete.php?id="+id,
        type: "DEL",
        success: function(result) {
        //console.log("done getall " + result);
        getBooks();
        },
    error: function(result) {
        //console.log("done getall " + result);
        getBooks();
    }});
}

//code for updating books
//ex id=5&title=pop&author=Popescu&pages=1000&genre=horror
document.getElementById("updateBooks").addEventListener("click", function() {
    updateBook();
})

function updateBook() {
    var input = "id="+ document.getElementById("id").value;
    input += "&title="+ document.getElementById("title").value;
    input += "&author="+ document.getElementById("author").value;
    input += "&pages="+ document.getElementById("pages").value;
    input += "&genre="+ document.getElementById("genre").value;
    // console.log(input);
    updateBooks(input);
}

function updateBooks(input) {
    console.log("start request");
    $.ajax({
        url: "api/update.php?"+input,
        type: "DEL",
        success: function(result) {
        //console.log("done getall " + result);
        getBooks();
        },
    error: function(result) {
        //console.log("done getall " + result);
        getBooks();
    }});
}