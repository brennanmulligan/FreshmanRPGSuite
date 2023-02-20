import 'dart:convert';

class CreatePlayerModel {
  final String name;
  final String password;
  final int majorID;
  final int crewID;
  final int section;

  CreatePlayerModel(
this.name, this.password, this.majorID, this.crewID, this.section);

  Map<String, dynamic> toMap(){
    return {
      'name': name,
      'password': password,
      'major': majorID,
      'crew': crewID,
      'section': section,
    };
  }
  
  String toJson() => json.encode(toMap());
  
  
}
