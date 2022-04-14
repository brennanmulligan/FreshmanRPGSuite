///
/// Types of responses we can get from the server.
///
enum ObjectiveResponseType {
  completed,
  alreadyCompleted,
  objectiveNotValid,
  questNotValid,
  outOfRange,
  networkFailure,
  dudQRCode,
}

extension ObjectiveResponseTypeExtension on ObjectiveResponseType {
  String description() {
    switch (this) {
      case ObjectiveResponseType.completed:
        return "Objective complete!";
      case ObjectiveResponseType.alreadyCompleted:
        return "Objective is already complete...";
      case ObjectiveResponseType.objectiveNotValid:
        return "Invalid objective...";
      case ObjectiveResponseType.questNotValid:
        return "Invalid quest...";
      case ObjectiveResponseType.outOfRange:
        return "Failed to complete objective... Move closer to the QR code.";
      case ObjectiveResponseType.networkFailure:
        return "Failed to complete objective... A network failure has occurred.";
      case ObjectiveResponseType.dudQRCode:
        return "This QR code is a dud. Please contact the school of engineering.";
    }
  }
}
