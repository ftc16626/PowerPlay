package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp(name = "Teleop", group = "Teleop")
public class Teleop extends LinearOpMode {

    //Power variables
    private final double intakePower = 1;

    //Position variables

    //Misc
    private final double slowPercentage = .3; //How much the wheels should be slowed by when the driver holds the right trigger

    //Class instantiation
    DriveTrain driveTrain = new DriveTrain();
    Arm arm = new arm();
    Claw claw = new claw();
    Lift lift = new Lift();

    @Override
    public void runOpMode() throws InterruptedException {

        //========================== CODE THAT WILL BE RUN DURING INITIALIZATION BELOW ==========================\\
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        driveTrain.init(hardwareMap);
        intake.init(hardwareMap);
        carousel.init(hardwareMap);
        lift.init(hardwareMap);

        //--------------------------- INIT LOOP ---------------------------\\

        while (!isStarted()) {
            lift.liftServo.setPosition(.85);
            lift.bucketServo.setPosition(.1);

        }

        //========================== CODE THAT WILL BE RUN AFTER INITIALIZATION BELOW ==========================\\

        //--------------------------- TELEOP LOOP ---------------------------\\


        while (!isStopRequested()) {


            //Gamepad 1 Controls
            gamePadOneControls();

            //Gamepad 2 Controls
            gamePadTwoControls();



            telemetry.addData("Position", lift.bucketServo.getPosition());
            telemetry.update();
        }
    }


    //========================== METHODS ==========================\\

    private void gamePadOneControls() {

        //Perhaps simpler because we just set the inputs to negative to reverse controls?
        if (gamepad1.left_bumper) {
            driveTrain.setDrivePower(gamepad1.right_trigger, slowPercentage, gamepad1.left_stick_y, -gamepad1.left_stick_x * 1.5, -gamepad1.right_stick_x);
        } else {
            driveTrain.setDrivePower(gamepad1.right_trigger, slowPercentage, -gamepad1.left_stick_y, gamepad1.left_stick_x * 1.5, gamepad1.right_stick_x);
        }
        carousel.rotate(gamepad1.left_stick_x > 0, gamepad1.left_stick_x < 0);

        //Works but perhaps unnecessarily complicated?
        /*
        if(!gamepad1.left_bumper){
            driveTrain.setDrivePower(gamepad1.right_trigger, slowPercentage, -gamepad1.left_stick_y, gamepad1.left_stick_x * 1.5, gamepad1.right_stick_x);
        }
        else driveTrain.setDrivePowerReversed(gamepad1.right_trigger, slowPercentage, -gamepad1.left_stick_y, -gamepad1.left_stick_x * 1.5, gamepad1.right_stick_x);
        //This is in both controls so that both drivers can use carousel if needed
        carousel.rotate(gamepad1.b, gamepad1.y);
         */
    }

    private void gamePadTwoControls() {
        intake.rotateIntake(gamepad2.right_stick_y> 0, gamepad2.right_stick_y < 0, intakePower);
        carousel.rotate(gamepad2.left_stick_x > 0, gamepad2.left_stick_x < 0);
        lift.elevate(gamepad2.a, gamepad2.b, gamepad2.y, gamepad2.x, gamepad2.left_stick_y, gamepad2.right_stick_y);


    }


}