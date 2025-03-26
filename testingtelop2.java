

    // todo: write your code here
package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import org.firstinspires.ftc.vision.tfod.TfodProcessor;


@TeleOp(name = "new truck")
public class Testingteleop extends LinearOpMode {

  private DcMotor backright;
  private DcMotor frontright;
  private DcMotor backleft;
  private DcMotor frontleft;
  //private IMU imu_IMU;
  //private Servo dronecannon;
  //private DcMotor tacotruck
  private DcMotor extension;
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double BackLeftPower;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    //IMU.Parameters gryoParameter;
    double headingDirection;
    double Power;
    double BackRightPower;
    double FrontLeftPower;
    double FrontRightPower;
    double DivideFactor;
    double SpeedMultiple;
    long delayStart = 0;

    backleft = hardwareMap.get(DcMotor.class, "backleft");
    frontleft = hardwareMap.get(DcMotor.class, "frontleft");
    backright = hardwareMap.get(DcMotor.class, "backright");
    frontright = hardwareMap.get(DcMotor.class, "frontright");
    //imu_IMU = hardwareMap.get(IMU.class, "imu");
        
    // Put initialization blocks here.
    backleft.setDirection(DcMotor.Direction.REVERSE);
    frontleft.setDirection(DcMotor.Direction.REVERSE);
    backright.setDirection(DcMotor.Direction.FORWARD);
    frontright.setDirection(DcMotor.Direction.FORWARD);
    // Create a new AprilTag Process Builder object
    // Creates a Parameters object for use with an IMU in a REV Robotics Control Hub or Expansion Hub, specifying the hub's arbitrary orientation on the robot via an Orientation block that describes the rotation that would need to be applied in order to rotate the hub from having its logo facing up and the USB ports facing forward, to its actual orientation on the robot.
    //gryoParameter = new IMU.Parameters(new RevHubOrientationOnRobot(new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, 0, 0, -45, 0)));
    // Initializes the IMU with non-default settings. To use this block,
    // plug one of the "new IMU.Parameters" blocks into the parameters socket.
    //imu_IMU.initialize(gryoParameter);
    backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    
    waitForStart();
    if (opModeIsActive()) {
      
      // Put run blocks here.
      //thingy1.setPosition(.1);
      while (opModeIsActive()){

            double power = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
            double theta = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
            double turn  =  gamepad1.right_stick_x;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            
            double sin = Math.sin(theta - Math.PI/4);
            double cos = Math.cos(theta - Math.PI/4);
            double max = Math.max(Math.abs(sin),Math.abs(cos));
            
            double leftFrontPower = power * sin/max + turn;
            double rightFrontPower = power * sin/max - turn;
            double leftRearPower = power * cos/max + turn;
            double rightRearPower = power * cos/max - turn;
            
            if ((power + Math.abs(turn)) > 1) {
             leftFrontPower /= power + turn;
             rightFrontPower /= power + turn;
              leftRearPower /= power + turn;
             rightRearPower /= power + turn;
             
                
            }
            //if()
            
            /*
            if the absolute value (gamepad_x - gamepad_y) == around 0.01
                decrease the power a bit //this will move the wheels one way or another*/
                
                double powerMultiplier = 1.0;
            if(gamepad1.left_trigger > 0.0)
            {
                powerMultiplier = 0.5; 
            }
            
            frontleft.setPower(leftFrontPower * powerMultiplier);
            backleft.setPower(leftRearPower * powerMultiplier);
            frontright.setPower(rightFrontPower * powerMultiplier);
            backright.setPower(rightRearPower * powerMultiplier);
        
      }
      
    }
        
    
  }
  
  private double max(double num1, double num2) {
    double maximum_number;

    if (num1 > num2) {
      maximum_number = num1;
    } else {
      maximum_number = num2;
    }
    return maximum_number;
  }     
  
}

