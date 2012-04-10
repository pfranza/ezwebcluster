package com.gorthaur.cluster.console.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

//	@Inject Provider<SkillManager> skillsManagerActivityProvider;
//	@Inject Provider<SkillEntryEditor> skillEditorActivityActivityProvider;
//	
//	@Inject Provider<CharactersManagerActivity> charactersManagerActivityProvider;
//	
//	@Inject Provider<DialogEditorActivity> dialogEditorActivityActivityProvider;
//	@Inject Provider<DialogManager> dialogManagerActivityProvider;
//	
//	@Inject Provider<GlossaryManager> glossaryManagerActivityProvider;
//	@Inject Provider<ChallengeManager> challengeManagerActivityProvider;
//	@Inject Provider<ChallengeEditorActivity> challengeEditorActivityProvider;
//	
//	@Inject Provider<LessonManager> lessonManagerActivityProvider;
//	@Inject Provider<PracticeProblemActivity> practiceProblemActivity;
//	@Inject Provider<LessonTextEditor> lessonTextEditorActivity;
//	@Inject Provider<LessonVideoEditorActivity> lessonVideoEditorActivity;
//	
//	@Inject Provider<ProblemsManager> problemsManagerProvider;
//	@Inject Provider<ProblemGroupEditor> problemGroupEditorProvider;
//
//	@Inject Provider<AdministrationActivity> administrationActivity;
//	
//	@Inject Provider<QAProblemActivity> qaProblemActivity;
//	@Inject Provider<QAProblemSummaryActivity> qaProblemSummaryActivity;
//	
//	@Inject Provider<UnitManagerActivity> unitManagerActivity;
//	
//	@Inject PermissionManager permissionManager;
	
	@Override
	public Activity getActivity(Place place) {
		
//		if(place instanceof SkillsPlace && hasOrPermission(Permission.CAN_VIEW_SKILLS, Permission.CAN_EDIT_SKILLS)) {
//			SkillsPlace sp = (SkillsPlace) place;
//			if(sp.getToken() == null || sp.getToken().equals("")) {
//				return skillsManagerActivityProvider.get();
//			} else {
//				SkillEntryEditor activity = skillEditorActivityActivityProvider.get();
//				activity.setPlace(sp);
//				return activity;
//			}
//		} else if(place instanceof CharactersPlace && hasOrPermission(Permission.CAN_VIEW_CHARACTERS, Permission.CAN_EDIT_CHARACTERS)) {
//			return charactersManagerActivityProvider.get();
//		} else if(place instanceof GlossaryPlace && hasOrPermission(Permission.CAN_VIEW_GLOSSAY, Permission.CAN_EDIT_GLOSSARY)) {
//			return glossaryManagerActivityProvider.get();
//		} else if(place instanceof DialogPlace && hasOrPermission(Permission.CAN_VIEW_DIALOG, Permission.CAN_EDIT_DIALOG)) {
//			DialogPlace sp = (DialogPlace) place;
//			if(sp.getCharacterId() == null || sp.getCharacterId().equals("")) {
//				return dialogManagerActivityProvider.get();
//			} else {
//				DialogEditorActivity activity = dialogEditorActivityActivityProvider.get();
//				activity.setPlace(sp);
//				return activity;
//			}
//		} else if(place instanceof ChallengePlace && hasOrPermission(Permission.CAN_VIEW_CHALLENGES, Permission.CAN_EDIT_CHALLENGES)) {
//			ChallengePlace sp = (ChallengePlace) place;
//			if(sp.getToken() == null || sp.getToken().equals("")) {
//				return challengeManagerActivityProvider.get();
//			} else {
//				ChallengeEditorActivity activity = challengeEditorActivityProvider.get();
//				activity.setPlace(sp);
//				return activity;
//			}
//		} else if(place instanceof LessonPlace && hasOrPermission(CAN_VIEW_LESSON, CAN_EDIT_LESSONS)) {
//			LessonPlace sp = (LessonPlace) place;
//			LessonManager activity = lessonManagerActivityProvider.get();
//			activity.setPlace(sp);
//			return activity;
//		} else if(place instanceof PracticeProblemEditorPlace && hasOrPermission(CAN_VIEW_LESSON, CAN_EDIT_LESSONS)) {
//			PracticeProblemEditorPlace sp = (PracticeProblemEditorPlace) place;
//			PracticeProblemActivity activity = practiceProblemActivity.get();
//			activity.setPlace(sp);
//			return activity;
//		} else if(place instanceof LessonTextEditorPlace && hasOrPermission(CAN_VIEW_LESSON, CAN_EDIT_LESSONS)) {
//			LessonTextEditorPlace sp = (LessonTextEditorPlace) place;
//			LessonTextEditor activity = lessonTextEditorActivity.get();
//			activity.setPlace(sp);
//			return activity;
//		} else if(place instanceof LessonVideoEditorPlace && hasOrPermission(CAN_VIEW_LESSON, CAN_EDIT_LESSONS)) {
//			LessonVideoEditorPlace sp = (LessonVideoEditorPlace) place;
//			LessonVideoEditorActivity activity = lessonVideoEditorActivity.get();
//			activity.setPlace(sp);
//			return activity;
//		} else if(place instanceof ProblemsPlace && hasOrPermission(CAN_VIEW_PROBLEMS, CAN_EDIT_PROBLEMS)) {
//			ProblemsPlace sp = (ProblemsPlace) place;
//			ProblemsManager activity = problemsManagerProvider.get();
//			activity.setPlace(sp);
//			return activity;
//		} else if(place instanceof ProblemGroupPlace && hasOrPermission(CAN_VIEW_PROBLEMS, CAN_EDIT_PROBLEMS)) {
//			ProblemGroupPlace sp = (ProblemGroupPlace) place;
//			ProblemGroupEditor activity = problemGroupEditorProvider.get();
//			activity.setPlace(sp);
//			return activity;
//		} else if(place instanceof AdministrationPlace && hasOrPermission(ADMINISTRATOR)) {
//			System.out.println("Admin Place");
//			return administrationActivity.get();
//		} else if(place instanceof QAProblemPlace && hasOrPermission(CAN_QA_PROBLEMS_CORRECTNESS, CAN_QA_PROBLEMS_LAYOUT)) {
//			QAProblemPlace sp = (QAProblemPlace) place;
//			QAProblemActivity qapa = qaProblemActivity.get();
//			qapa.setToken(sp);
//			return qapa;
//		} else if(place instanceof QAProblemSummaryPlace && hasOrPermission(CAN_QA_PROBLEMS_CORRECTNESS, CAN_QA_PROBLEMS_LAYOUT)) {
//			return qaProblemSummaryActivity.get();
//		} else if(place instanceof TOCUnitManagerPlace && hasOrPermission(CAN_CREATE_LESSONS)) { 
//			TOCUnitManagerPlace sp = (TOCUnitManagerPlace) place;
//			UnitManagerActivity qapa = unitManagerActivity.get();
//			qapa.setToken(sp);
//			return qapa;
//		}
		
		System.out.println("No Activity Mapped For Place: " + place);
		return null;
	}
	
	

}
