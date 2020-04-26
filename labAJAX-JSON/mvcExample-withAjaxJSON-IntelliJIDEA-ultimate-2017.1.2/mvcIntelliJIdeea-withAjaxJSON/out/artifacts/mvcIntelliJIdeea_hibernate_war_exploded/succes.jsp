<%@ page import="webubb.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: forest
  Date: 16.12.2014
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <style>
        .asset-name {
            background-color: cornflowerblue;
            border-right: solid 1px black;
        }
    </style>
    <script src="js/jquery-2.0.3.js"></script>
    <script src="js/ajax-utils.js"></script>
</head>
<body>
<%! User user; %>
<%  user = (User) session.getAttribute("user");
    if (user != null) {
        out.println("Welcome "+user.getUsername());
%>
        <br/>
        <p><button id="getQuestionsbtn" type="button">Get Questions</button></p>
        <section><table id="question-table"></table></section>
        <p><button id="getResultbtn" type="button">Get Result</button></p>
        <section id="get-result-section"></section>
        <p><button id="getAllTimeResultbtn" type="button">Get All Time Result</button></p>
        <section id="get-result1-section"></section>
        <p style="height: 50px;"></p>
        <%--<section id="update-section">--%>
            <%--<span style="font-weight: bold; background-color: mediumseagreen">Update asset</span><br/>--%>
            <%--<table>--%>
                <%--<tr><td>ID asset: </td><td><input type="text" id="asset-id"></td></tr>--%>
                <%--<tr><td>Asset userid: </td><td><input type="text" id="asset-userid"></td></tr>--%>
                <%--<tr><td>Asset description: </td><td><input type="text" id="asset-description"></td></tr>--%>
                <%--<tr><td>Asset value: </td><td><input type="text" id="asset-value"></td></tr>--%>
                <%--<tr><td><button type="button" id="update-asset-btn">Update asset</button></td><td></td></tr>--%>
            <%--</table>--%>
        <%--</section>--%>
        <%--<section id="update-result-section"></section>--%>


        <script>
                $("#update-asset-btn").click(function() {
                    updateAsset($("#asset-id").val(),
                        $("#asset-userid").val(),
                        $("#asset-description").val(),
                        $("#asset-value").val(),
                        function(response) {
                            $("#update-result-section").html(response);
                        }
                    )
                })

                // function getUserQuestions(userid, callbackFunction) {
                //     $.getJSON(
                //         "QuestionsController",
                //         { action: 'getAll', userid: userid },
                //         callbackFunction
                //     );
                // }

                $("#getQuestionsbtn").click(function() {
                    getUserQuestions(<%= user.getId() %>, function(questions) {
                        console.log(questions);
                        $("#question-table").html("");
                        $("#question-table").append("<tr style='background-color: mediumseagreen'><td>Id</td><td>Description</td><td>Answer1</td><td>Answer2</td><td>Answer3</td><td>Answer4</td></tr>");
                        for(var name in questions) {
                            //console.log(assets[name].description);
                            $("#question-table").append("<tr>" +
                                "<td class='question-name'>"+questions[name].id+"</td>" +
                                "<td>"+questions[name].description+"</td>" +
                                "<td>"+questions[name].answer1+"</td>" +
                                "<td>"+questions[name].answer2+"</td>" +
                                "<td>"+questions[name].answer3+"</td>" +
                                "<td>"+questions[name].answer4+"</td> " +
                                "<td>"+
                                "<select id=Answer"+questions[name].id+">" +
                                "<option>answer1</option>" +
                                "<option>answer2</option>" +
                                "<option>answer3</option>" +
                                "<option>answer4</option>" +
                                "</select></td>"+
                                "</tr>");
                        };
                    })
                })
                $("#getResultbtn").click(function() {
                    getUserQuestions(<%= user.getId() %>,
                        function(questions) {
                        //here you have to do a for in order to access all the answers
                            console.log(questions)
                            var correct=0;
                            var wrong=0;
                            var str;
                            for(var name in questions) {
                                var sel = document.getElementById("Answer" + questions[name].id);
                                if (sel.options[sel.selectedIndex].text === questions[name].correctAnswer) correct += 1;
                                else wrong += 1;
                            }
                            getUserScore( <%= user.getId() %>, correct, function(allTimeScore){
                                console.log(allTimeScore);
                                $("#get-result-section").html("All Time Correct Answers:"+allTimeScore);});
                            $("#get-result-section").html("Correct answers:"+correct+" Wrong Answers: "+wrong);
                        }
                    )
                })
                $("#getAllTimeResultbtn").click(function() {
                    getUserAllTimeScore(<%= user.getId() %>, function(user1) {
                        console.log(user1);
                        for(var name in user1) {
                            $("#get-result1-section").html("All Time Correct Answers:"+user1[name].allTimeScore);
                        }
                    })
                }
                )



        </script>
<%
    }
%>

</body>
</html>