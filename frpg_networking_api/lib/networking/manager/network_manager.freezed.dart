// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'network_manager.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$NetworkManagerStateTearOff {
  const _$NetworkManagerStateTearOff();

  _NetworkManagerState call({dynamic baseURL = 'localhost:8080'}) {
    return _NetworkManagerState(
      baseURL: baseURL,
    );
  }
}

/// @nodoc
const $NetworkManagerState = _$NetworkManagerStateTearOff();

/// @nodoc
mixin _$NetworkManagerState {
  dynamic get baseURL => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $NetworkManagerStateCopyWith<NetworkManagerState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $NetworkManagerStateCopyWith<$Res> {
  factory $NetworkManagerStateCopyWith(
          NetworkManagerState value, $Res Function(NetworkManagerState) then) =
      _$NetworkManagerStateCopyWithImpl<$Res>;
  $Res call({dynamic baseURL});
}

/// @nodoc
class _$NetworkManagerStateCopyWithImpl<$Res>
    implements $NetworkManagerStateCopyWith<$Res> {
  _$NetworkManagerStateCopyWithImpl(this._value, this._then);

  final NetworkManagerState _value;
  // ignore: unused_field
  final $Res Function(NetworkManagerState) _then;

  @override
  $Res call({
    Object? baseURL = freezed,
  }) {
    return _then(_value.copyWith(
      baseURL: baseURL == freezed
          ? _value.baseURL
          : baseURL // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ));
  }
}

/// @nodoc
abstract class _$NetworkManagerStateCopyWith<$Res>
    implements $NetworkManagerStateCopyWith<$Res> {
  factory _$NetworkManagerStateCopyWith(_NetworkManagerState value,
          $Res Function(_NetworkManagerState) then) =
      __$NetworkManagerStateCopyWithImpl<$Res>;
  @override
  $Res call({dynamic baseURL});
}

/// @nodoc
class __$NetworkManagerStateCopyWithImpl<$Res>
    extends _$NetworkManagerStateCopyWithImpl<$Res>
    implements _$NetworkManagerStateCopyWith<$Res> {
  __$NetworkManagerStateCopyWithImpl(
      _NetworkManagerState _value, $Res Function(_NetworkManagerState) _then)
      : super(_value, (v) => _then(v as _NetworkManagerState));

  @override
  _NetworkManagerState get _value => super._value as _NetworkManagerState;

  @override
  $Res call({
    Object? baseURL = freezed,
  }) {
    return _then(_NetworkManagerState(
      baseURL: baseURL == freezed ? _value.baseURL : baseURL,
    ));
  }
}

/// @nodoc

class _$_NetworkManagerState extends _NetworkManagerState
    with DiagnosticableTreeMixin {
  const _$_NetworkManagerState({this.baseURL = 'localhost:8080'}) : super._();

  @JsonKey()
  @override
  final dynamic baseURL;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'NetworkManagerState(baseURL: $baseURL)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'NetworkManagerState'))
      ..add(DiagnosticsProperty('baseURL', baseURL));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _NetworkManagerState &&
            const DeepCollectionEquality().equals(other.baseURL, baseURL));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(baseURL));

  @JsonKey(ignore: true)
  @override
  _$NetworkManagerStateCopyWith<_NetworkManagerState> get copyWith =>
      __$NetworkManagerStateCopyWithImpl<_NetworkManagerState>(
          this, _$identity);
}

abstract class _NetworkManagerState extends NetworkManagerState {
  const factory _NetworkManagerState({dynamic baseURL}) =
      _$_NetworkManagerState;
  const _NetworkManagerState._() : super._();

  @override
  dynamic get baseURL;
  @override
  @JsonKey(ignore: true)
  _$NetworkManagerStateCopyWith<_NetworkManagerState> get copyWith =>
      throw _privateConstructorUsedError;
}
