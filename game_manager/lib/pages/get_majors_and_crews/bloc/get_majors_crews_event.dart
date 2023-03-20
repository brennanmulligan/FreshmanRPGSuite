/*
 creates a majors and crews event class
 /

 */
part of 'get_majors_and_crews_bloc.dart';
abstract class GetMajorsAndCrewsEvent{}

class SendCreateMajorsCrewsEvent extends GetMajorsAndCrewsEvent {

    final num crews;
    final num  majors;


    SendCreateMajorsCrewsEvent(this.crews, this.majors);
/*
returns the major and crews event string
 */

@override
  String toString() {
  return 'SendCreateMajorsCrewsEvent(crew: $crews, major: $majors)';
}
}