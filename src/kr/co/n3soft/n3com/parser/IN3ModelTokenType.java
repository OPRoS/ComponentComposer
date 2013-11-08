package kr.co.n3soft.n3com.parser;

import java.io.StreamTokenizer;

public interface IN3ModelTokenType {
	public static final int 
	TT_EOF = StreamTokenizer.TT_EOF, 
	TT_EOL = StreamTokenizer.TT_EOL, 
	TT_NUMBER = StreamTokenizer.TT_NUMBER, 
	TT_WORD = StreamTokenizer.TT_WORD;

	public static final int
	_str_token = 100, _mark_token = 200, _stmt_token = 300, _emit = 400;
	
	public static final String 
	PROPERTY_N3EOF = "PROPERTY_N3EOF";
	
	
	
	public static final int 
	e_nothing			= -1,
	e_push				= _emit + 1,
	e_models_begin = _emit + 11,
	e_create_model = _emit + 12,
	e_model_begin		= _emit + 13,
	e_model_propertys_begin		= _emit + 14,
	e_property_push		= _emit + 15,
	e_property_begin		= _emit + 16,
	e_object_create_objectType		= _emit + 17,
	e_objects_begin		= _emit + 18,
	
	e_objects_end = _emit + 19,
	e_propertys_list_end = _emit + 20,
	e_model_propertys_end		= _emit + 21,
	e_model_end		= _emit + 22,
	e_models_end		= _emit + 23,
	
	
	e_views_begin = _emit + 24,
	e_create_view = _emit + 25,
	e_view_begin		= _emit + 26,
	e_view_propertys_begin		= _emit + 27,
	e_view_property_push		= _emit + 28,
	e_view_property_begin		= _emit + 29,
	e_view_object_create_objectType		= _emit + 30,
	e_view_objects_begin		= _emit + 31,
	
	e_view_objects_end = _emit + 32,
	e_view_propertys_list_end = _emit + 33,
	e_view_propertys_end		= _emit + 34,
	e_view_end		= _emit + 35,
	e_views_end		= _emit + 36,
	e_propertys_list_begin		= _emit + 37,
	e_lines_begin = _emit + 38,
	
	
	
	e_model_create = _emit + 24,
	e_model_set_property = _emit + 25,
//	e_model_begin		= _emit + 11,
	e_model_property_begin		= _emit + 26,
	e_model_key_begin		= _emit + 27,
	e_model_value_begin		= _emit + 28,
	e_child_models_begin		= _emit + 29,
//	e_model_end		= _emit + 16,
	e_model_property_end		= _emit + 30,
	e_model_key_end		= _emit + 31,
	e_model_value_end		= _emit + 32
	;
	
	public static final int
	t_id = 1,
	t_eof = 2,
	t_error = 3,
	t_until_key_value= 4,
	
	
	
	
	s_models		= _str_token + 0,
	s_model		= _str_token + 1,
	s_model_propertys		= _str_token + 2,
	s_property =  _str_token + 3,
	
	s_propertys_list		= _str_token + 4,
	s_objects		= _str_token + 5,
	
	s_views		= _str_token + 6,
	s_view		= _str_token + 7,
	s_view_propertys		= _str_token + 8,
	s_view_property =  _str_token + 9,
	
	s_view_propertys_list		= _str_token + 10,
	s_view_objects		= _str_token + 11,
    s_lines =  _str_token + 12,
	
	
	
	s_child_models_begin		= _str_token + 5,
	s_model_end		= _str_token + 6,
	s_model_property_end		= _str_token + 7,
	s_model_key_end		= _str_token + 8,
	s_model_value_end		= _str_token + 9,
	s_semicolon 	= _mark_token + 0,
	s_s_begin 		= _mark_token + 1, 
	s_s_end 		= _mark_token + 2,
	s_assign 		= _mark_token + 3,
	s_quotation 		= _mark_token + 4,
	s_l_begin 		= _mark_token + 5,
	s_l_end 		= _mark_token + 6,
	
	t_until = 4,
//	e_nothing			= -1,
//	e_push				= _emit + 1,
	t_models_begin		= _str_token + 0,
	t_model_begin		= _str_token + 1,
	t_model_property_begin		= _str_token + 2,
	t_model_property =  _str_token + 3,
	
	t_model_key_begin		= _str_token + 3,
	t_model_value_begin		= _str_token + 4,
	t_child_models_begin		= _str_token + 5,
	t_model_end		= _str_token + 6,
	t_model_property_end		= _str_token + 7,
	t_model_key_end		= _str_token + 8,
	t_model_value_end		= _str_token + 9,
	t_semicolon 	= _mark_token + 0,
	t_s_begin 		= _mark_token + 1, 
	t_s_end 		= _mark_token + 2,
	t_assign 		= _mark_token + 3,
	t_quotation 		= _mark_token + 4,
	t_l_begin 		= _mark_token + 5,
	t_l_end 		= _mark_token + 6
	
	;

public static final int 
//	e_nothing			= -1,
//	e_push				= _emit + 1,
	
//	e_class_begin		= _emit + 10,
	e_interface_begin	= _emit + 11,
	e_class_id			= _emit + 12,
	e_class_end			= _emit + 13,
	
	e_package			= _emit + 21,
	e_import			= _emit + 31,
	e_extends			= _emit + 41,
	e_implements		= _emit + 51,

	e_field				= _emit + 60,
	e_more_field		= _emit + 61,
	e_array				= _emit + 62,
	e_name				= _emit + 63,
	e_array_after_field	= _emit + 65,

	e_method			= _emit + 71,
	e_parameter			= _emit + 72,
	e_parameter_name	= _emit + 73,
	e_throws			= _emit + 74,
	e_array_after_method= _emit + 75,
	
	e_constructor		= _emit + 81,
	e_static_block		= _emit + 82,

	e_block				= _emit + 91,
	e_expr				= _emit + 92,

	e_option			= _emit + 1000;

public static final int
//	t_id = 1,
//	t_eof = 2,
//	t_error = 3,
	
	t_package 		= _str_token + 0, 
	t_import 		= _str_token + 1, 
	t_class 		= _str_token + 2, 
	t_interface 	= _str_token + 3,
	t_extends 		= _str_token + 4, 
	t_implements 	= _str_token + 5,
	t_public 		= _str_token + 6, 
	t_protected 	= _str_token + 7, 
	t_private 		= _str_token + 8,
	t_abstract 		= _str_token + 9, 
	t_strictfp 		= _str_token + 10,
	t_static 		= _str_token + 11, 
	t_final 		= _str_token + 12, 
	t_volatile 		= _str_token + 13, 
	t_transient 	= _str_token + 14,
	t_synchronized 	= _str_token + 15, 
	t_native 		= _str_token + 16, 
	t_throws 		= _str_token + 17,
	
	
	t_mark_models_begin = _mark_token + 0, 
	t_mark_model_begin = _mark_token + 1, 
	t_mark_model_propertys_begin = _mark_token + 2, 
	t_mark_propertys_list_begin = _mark_token + 3, 
	t_mark_objects_begin = _mark_token + 4, 
	t_mark_objects_end= _mark_token + 5, 
	
	
	t_mark_propertys_list_end = _mark_token + 6, 
	t_mark_model_propertys_end= _mark_token + 7, 
	t_mark_model_end = _mark_token + 8, 
	t_mark_models_end = _mark_token + 9, 
	t_mark_end = _mark_token + 10, 
	
	
//	t_semicolon 	= _mark_token + 0, 
	t_dot 			= _mark_token + 1, 
	t_comma 		= _mark_token + 2, 
//	t_assign 		= _mark_token + 3, 
	t_star 			= _mark_token + 4,
//	t_l_begin 		= _mark_token + 5, 
//	t_l_end 		= _mark_token + 6, // l: [] m: {} s: ()
//	t_m_begin 		= _mark_token + 7, 
	t_m_end 		= _mark_token + 8, 
//	t_s_begin 		= _mark_token + 9, 
//	t_s_end 		= _mark_token + 10,

	t_block			= _stmt_token + 0,
	t_expr			= _stmt_token + 1;

//public static final String string_tokens[] = {
//	"package", "import", "class", "interface", "extends",
//	"implements", "public", "protected", "private", "abstract",
//	"strictfp", "static", "final", "volatile", "transient", "synchronized",
//	"native", "throws"
//};

public static final String string_tokens[] = {
	"models","model","model_propertys","property","propertys_list","objects","views","view","view_propertys","view_property","vlew_propertys_list","views_objects","lines"
	
};


//public static final char mark_tokens[] = {
//	';', '.', ',', '=', '*', '[', ']', '{', '}', '(', ')'
//};

public static final char mark_tokens[] = {
'{', '(', '<', '[','+','-',']',  '>', ')', '}',';'};

}
