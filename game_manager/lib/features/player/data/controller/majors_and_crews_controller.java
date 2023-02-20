import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:game_manager/features/player/data/data.dart';

part 'majors_and_crews.freezed.dart';

// https://code-with-me.global.jetbrains.com/0O-5DRBf4bNdyHqVzkOfbg#p=IU&fp=7C83B720229CB5BE01EE1574E7D83F234281FE6D13C2E7FE0849920DA267ABE9

// State for the controller

@freezed
class MajorsAndCrewsState with _$MajorsAndCrewsState {
    const factory MajorsAndCrewsState{(
        @Default(false) showResponse,
        GetMajorsAndCrewsResponse? getMajorsAndCrewsResponse,
    )} = _PlayerState._();
}

Future<void> getMajorsAndCrews(
    String[] majors, String[] crews) async {
    final reponse = await _repository.getMajorsAndCrews(
        request: GetMajorsAndCrewsRequest(
           majors: majors,
           crews: crews
        )
    );
    GetMajorsAndCrewsResponse getMajorsAndCrewsResponse = response.when()
}