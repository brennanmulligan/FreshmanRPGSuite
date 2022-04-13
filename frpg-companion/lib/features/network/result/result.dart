import 'package:freezed_annotation/freezed_annotation.dart';
import 'failure.dart';
part 'result.freezed.dart';

///
/// Wrapper for `Future` responses. Used to prevent crashes caused
/// by runtime exceptions. Result can either be an instance of ResultData
/// or ResultFailure. If an exception is thrown, then `Result` is an
/// instance of `ResultFailure`. If data is received, then `Result` is an
/// instance of `ResultData`.
///
@freezed
class Result<T> with _$Result<T> {
  ///
  /// Creates an instance of `ResultData` from data `T`.
  ///
  factory Result.data({
    required T data,
  }) = ResultData;

  ///
  /// Creates and instance of `ResultFailure` from failure `Failure`.
  ///
  factory Result.failure({
    required Failure failure,
  }) = ResultFailure;
}

///
/// Extension on `Result`.
///
extension ResultExtension on Result {
  ///
  /// Check if `Result` is an instance of `ResultData`.
  ///
  bool get isData => this is ResultData;

  ///
  /// Check if `Result` is an instance of `ResultFailure`.
  ///
  bool get isFailure => this is ResultFailure;
}
