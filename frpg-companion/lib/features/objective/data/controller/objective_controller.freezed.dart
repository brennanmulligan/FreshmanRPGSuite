// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'objective_controller.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$ObjectiveStateTearOff {
  const _$ObjectiveStateTearOff();

  _ObjectiveState call(
      {dynamic showResponse = false,
      dynamic playerID = 0,
      dynamic playerName = "",
      CompleteObjectiveResponse? objectiveResponse,
      FetchAllObjectiveResponse? fetchAllObjectiveResponse}) {
    return _ObjectiveState(
      showResponse: showResponse,
      playerID: playerID,
      playerName: playerName,
      objectiveResponse: objectiveResponse,
      fetchAllObjectiveResponse: fetchAllObjectiveResponse,
    );
  }
}

/// @nodoc
const $ObjectiveState = _$ObjectiveStateTearOff();

/// @nodoc
mixin _$ObjectiveState {
  dynamic get showResponse => throw _privateConstructorUsedError;
  dynamic get playerID => throw _privateConstructorUsedError;
  dynamic get playerName => throw _privateConstructorUsedError;
  CompleteObjectiveResponse? get objectiveResponse =>
      throw _privateConstructorUsedError;
  FetchAllObjectiveResponse? get fetchAllObjectiveResponse =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ObjectiveStateCopyWith<ObjectiveState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ObjectiveStateCopyWith<$Res> {
  factory $ObjectiveStateCopyWith(
          ObjectiveState value, $Res Function(ObjectiveState) then) =
      _$ObjectiveStateCopyWithImpl<$Res>;
  $Res call(
      {dynamic showResponse,
      dynamic playerID,
      dynamic playerName,
      CompleteObjectiveResponse? objectiveResponse,
      FetchAllObjectiveResponse? fetchAllObjectiveResponse});
}

/// @nodoc
class _$ObjectiveStateCopyWithImpl<$Res>
    implements $ObjectiveStateCopyWith<$Res> {
  _$ObjectiveStateCopyWithImpl(this._value, this._then);

  final ObjectiveState _value;
  // ignore: unused_field
  final $Res Function(ObjectiveState) _then;

  @override
  $Res call({
    Object? showResponse = freezed,
    Object? playerID = freezed,
    Object? playerName = freezed,
    Object? objectiveResponse = freezed,
    Object? fetchAllObjectiveResponse = freezed,
  }) {
    return _then(_value.copyWith(
      showResponse: showResponse == freezed
          ? _value.showResponse
          : showResponse // ignore: cast_nullable_to_non_nullable
              as dynamic,
      playerID: playerID == freezed
          ? _value.playerID
          : playerID // ignore: cast_nullable_to_non_nullable
              as dynamic,
      playerName: playerName == freezed
          ? _value.playerName
          : playerName // ignore: cast_nullable_to_non_nullable
              as dynamic,
      objectiveResponse: objectiveResponse == freezed
          ? _value.objectiveResponse
          : objectiveResponse // ignore: cast_nullable_to_non_nullable
              as CompleteObjectiveResponse?,
      fetchAllObjectiveResponse: fetchAllObjectiveResponse == freezed
          ? _value.fetchAllObjectiveResponse
          : fetchAllObjectiveResponse // ignore: cast_nullable_to_non_nullable
              as FetchAllObjectiveResponse?,
    ));
  }
}

/// @nodoc
abstract class _$ObjectiveStateCopyWith<$Res>
    implements $ObjectiveStateCopyWith<$Res> {
  factory _$ObjectiveStateCopyWith(
          _ObjectiveState value, $Res Function(_ObjectiveState) then) =
      __$ObjectiveStateCopyWithImpl<$Res>;
  @override
  $Res call(
      {dynamic showResponse,
      dynamic playerID,
      dynamic playerName,
      CompleteObjectiveResponse? objectiveResponse,
      FetchAllObjectiveResponse? fetchAllObjectiveResponse});
}

/// @nodoc
class __$ObjectiveStateCopyWithImpl<$Res>
    extends _$ObjectiveStateCopyWithImpl<$Res>
    implements _$ObjectiveStateCopyWith<$Res> {
  __$ObjectiveStateCopyWithImpl(
      _ObjectiveState _value, $Res Function(_ObjectiveState) _then)
      : super(_value, (v) => _then(v as _ObjectiveState));

  @override
  _ObjectiveState get _value => super._value as _ObjectiveState;

  @override
  $Res call({
    Object? showResponse = freezed,
    Object? playerID = freezed,
    Object? playerName = freezed,
    Object? objectiveResponse = freezed,
    Object? fetchAllObjectiveResponse = freezed,
  }) {
    return _then(_ObjectiveState(
      showResponse:
          showResponse == freezed ? _value.showResponse : showResponse,
      playerID: playerID == freezed ? _value.playerID : playerID,
      playerName: playerName == freezed ? _value.playerName : playerName,
      objectiveResponse: objectiveResponse == freezed
          ? _value.objectiveResponse
          : objectiveResponse // ignore: cast_nullable_to_non_nullable
              as CompleteObjectiveResponse?,
      fetchAllObjectiveResponse: fetchAllObjectiveResponse == freezed
          ? _value.fetchAllObjectiveResponse
          : fetchAllObjectiveResponse // ignore: cast_nullable_to_non_nullable
              as FetchAllObjectiveResponse?,
    ));
  }
}

/// @nodoc

class _$_ObjectiveState extends _ObjectiveState with DiagnosticableTreeMixin {
  const _$_ObjectiveState(
      {this.showResponse = false,
      this.playerID = 0,
      this.playerName = "",
      this.objectiveResponse,
      this.fetchAllObjectiveResponse})
      : super._();

  @JsonKey()
  @override
  final dynamic showResponse;
  @JsonKey()
  @override
  final dynamic playerID;
  @JsonKey()
  @override
  final dynamic playerName;
  @override
  final CompleteObjectiveResponse? objectiveResponse;
  @override
  final FetchAllObjectiveResponse? fetchAllObjectiveResponse;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'ObjectiveState(showResponse: $showResponse, playerID: $playerID, playerName: $playerName, objectiveResponse: $objectiveResponse, fetchAllObjectiveResponse: $fetchAllObjectiveResponse)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'ObjectiveState'))
      ..add(DiagnosticsProperty('showResponse', showResponse))
      ..add(DiagnosticsProperty('playerID', playerID))
      ..add(DiagnosticsProperty('playerName', playerName))
      ..add(DiagnosticsProperty('objectiveResponse', objectiveResponse))
      ..add(DiagnosticsProperty(
          'fetchAllObjectiveResponse', fetchAllObjectiveResponse));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _ObjectiveState &&
            const DeepCollectionEquality()
                .equals(other.showResponse, showResponse) &&
            const DeepCollectionEquality().equals(other.playerID, playerID) &&
            const DeepCollectionEquality()
                .equals(other.playerName, playerName) &&
            const DeepCollectionEquality()
                .equals(other.objectiveResponse, objectiveResponse) &&
            const DeepCollectionEquality().equals(
                other.fetchAllObjectiveResponse, fetchAllObjectiveResponse));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(showResponse),
      const DeepCollectionEquality().hash(playerID),
      const DeepCollectionEquality().hash(playerName),
      const DeepCollectionEquality().hash(objectiveResponse),
      const DeepCollectionEquality().hash(fetchAllObjectiveResponse));

  @JsonKey(ignore: true)
  @override
  _$ObjectiveStateCopyWith<_ObjectiveState> get copyWith =>
      __$ObjectiveStateCopyWithImpl<_ObjectiveState>(this, _$identity);
}

abstract class _ObjectiveState extends ObjectiveState {
  const factory _ObjectiveState(
          {dynamic showResponse,
          dynamic playerID,
          dynamic playerName,
          CompleteObjectiveResponse? objectiveResponse,
          FetchAllObjectiveResponse? fetchAllObjectiveResponse}) =
      _$_ObjectiveState;
  const _ObjectiveState._() : super._();

  @override
  dynamic get showResponse;
  @override
  dynamic get playerID;
  @override
  dynamic get playerName;
  @override
  CompleteObjectiveResponse? get objectiveResponse;
  @override
  FetchAllObjectiveResponse? get fetchAllObjectiveResponse;
  @override
  @JsonKey(ignore: true)
  _$ObjectiveStateCopyWith<_ObjectiveState> get copyWith =>
      throw _privateConstructorUsedError;
}
