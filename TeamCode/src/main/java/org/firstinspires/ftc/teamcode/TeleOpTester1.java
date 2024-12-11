package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp | Tank")
public class teleopMaize_Red extends OpMode {
  
    double forward=0;
    double strafe=0;
    double rotate=0;


    boolean a_prev = false;
    boolean a_state = false;  
  
    public double MAXSPEED = .75;
    TankDrive drive = new TankDrive();

    public void init(){
      telemetry.addLine("init complete");
      telemetry.update();
    }

    @Override
    public void loop() {
       
      strafe = (gamepad1.left_trigger - gamepad1.right_trigger) * MAXSPEED;
      rotate = gamepad1.right_stick_x * MAXSPEED;
      forward = -gamepad1.left_stick_y * MAXSPEED;

        //Turtle Mode
        if (gamepad1.a && !a_prev) {
            if(!a_state) {
                MAXSPEED = 0.3;
                a_state = true;
            } else {
                MAXSPEED = 0.85;
                a_state = false;
            }
        }

        drive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(
                            forward,
                            strafe),
                            -rotate
                    ));
      
        telemetry.addData("lift",gamepad2.right_stick_y);
        telemetry.update();

    }
}
