<?php
    header('Access-Control-Allow-Origin: *');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: PUT');


    //asa se defineste o variabila
    $host = "localhost";
    $db_name = "PersonalLibrary";
    $username = "root";
    $password = "";
    $conn = null;

    try { 
        $conn = new PDO('mysql:host=' . $host . ';dbname=' . $db_name, $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    } catch(PDOException $e) {
        echo 'Connection Error: ' . $e->getMessage();
    }
    
    //$data = json_decode(file_get_contents('php://input'));
    $id = $_GET['id'];
    $title = $_GET['title'];
    $author = $_GET['author'];
    $pages = $_GET['pages'];
    $genre = $_GET['genre'];

    $query1= "UPDATE book ".' SET title = :title, author = :author, pages = :pages, genre = :genre WHERE id= :id ';
    $stmt = $conn->prepare($query1);
    $stmt->bindParam(':id', $id);
    $stmt->bindParam(':title', $title);
    $stmt->bindParam(':author', $author);
    $stmt->bindParam(':pages', $pages);
    $stmt->bindParam(':genre', $genre);
    if($stmt->execute()){
        echo json_encode(
            array('message' => 'Book Updated')
        );
    }else{
        echo json_encode(
            array('message' => 'Book Not Updated')
        );
    }