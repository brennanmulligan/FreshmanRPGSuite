import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:game_manager/features/player/data/data.dart';

part 'majors_and_crews.freezed.dart';

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