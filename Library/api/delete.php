<?php
    header('Access-Control-Allow-Origin: *');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: DELETE');


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

    $query1= "DELETE FROM book ".' WHERE id= :id ';
    $stmt = $conn->prepare($query1);
    $stmt->bindParam(':id', $id);
    
    if($stmt->execute()){
        echo json_encode(
            array('message' => 'Book Deleted')
        );
    }else{
        echo json_encode(
            array('message' => 'Book Not Deleted')
        );
    }