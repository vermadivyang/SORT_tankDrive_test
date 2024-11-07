/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.JoeBot;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.List;

public abstract class AbstractAutonomousOpMode extends OpMode
{
  private final Team team;
  private final GameStrategy gameStrategy;
  private AutonomousState state = AutonomousState.HAVE_SPECIMEN;
  private int neutralSamplesLeft = 6;
  private int teamSamplesLeft = 3;
  ElapsedTime time = null;
  List<LynxModule> hubs;
  JoeBot robot = null;

  protected AbstractAutonomousOpMode( Team team, GameStrategy gameStrategy )
  {
    this.team = team;
    this.gameStrategy = gameStrategy;
  }

  //We run this when the user hits "INIT" on the app
  @Override
  public void init()
  {
    time = new ElapsedTime();

    //setup bulk reads
    hubs = hardwareMap.getAll( LynxModule.class );
    for( LynxModule module : hubs )
    {
      module.setBulkCachingMode( LynxModule.BulkCachingMode.MANUAL );
    }

    robot = new JoeBot( true, hardwareMap, telemetry );

    telemetry.addLine( "Initialized Auto" );
    telemetry.update();
  }

  @Override
  public void init_loop()
  {
    //Allow robot to be pushed around before the start button is pressed
    robot.drive().coast();
  }

  @Override
  public void start()
  {
    //Prevent robot from being pushed around
    robot.drive().brake();
  }

  @Override
  public void loop()
  {
    //Clear the BulkCache once per control cycle
    for( LynxModule module : hubs )
    {
      module.clearBulkCache();
    }

    switch( gameStrategy )
    {
      case PARK:
        parkStrategy();
        break;
        case PLACE_SAMPLES_IN_BASKETS:
         bucketStrategy();
         break;
        case HANG_SPECIMENS_ON_BARS:
          specimenStrategy();
          break;
    }

    /*
    //Move the lift in the opposite direction once it has stopped moving
    if( !robot.lift().isMoving() )
    {
      if( state == State.LIFT_IS_DOWN )
      {
        robot.lift().travelTo( Lift.Position.LOW_BASKET );
        state = State.LIFT_IS_UP;
      }
      else
      {
        robot.lift().travelTo( Lift.Position.FLOOR );
        state = State.LIFT_IS_DOWN;
      }
    }

    MecanumDrive drive = robot.mecanumDrive();

    TrajectoryActionBuilder test = drive.actionBuilder( drive.pose )
      .splineTo( new Vector2d( 10, 20 ), Math.toRadians( 90 ) )
      .waitSeconds(2)
      .lineToYSplineHeading(33, Math.toRadians(0))
      .waitSeconds(2)
      .setTangent(Math.toRadians(90))
      .lineToY(48)
      .setTangent(Math.toRadians(0))
      .lineToX(32)
      .strafeTo(new Vector2d(44.5, 30))
      .turn(Math.toRadians(180))
      .lineToX(47.5);

    Action action = test.build();
    Actions.runBlocking(
      new SequentialAction(
        action
      )
    );
*/
  }

  private void parkStrategy()
  {
    //TODO
    //bucket bot parks by touching bar - first drive to location just outside hang zone to avoid hitting foot
    //  specimen bot parks by going to observation zone
  }

  private void bucketStrategy()
  {
    //TODO
  }

  private void specimenStrategy()
  {
    //TODO
  }
}
