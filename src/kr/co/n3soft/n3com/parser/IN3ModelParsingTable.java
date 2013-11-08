package kr.co.n3soft.n3com.parser;

public interface IN3ModelParsingTable extends IN3ModelTokenType{
	public static final int STATE_BEGIN = 0, STATE_END = 44,
	STATE_CLASS_BODY = 15;
	public static final int [][][] _table = {
		//0
		{
			{s_models,1,e_nothing},
			{s_model,2,e_nothing},
			{s_property,6,e_nothing},
			{t_mark_objects_end,10,e_objects_end},
			{t_mark_end,44,-1},
			{s_views,0,e_views_begin},
			{s_lines,0,e_lines_begin}
		},
		//1
		{
			{t_mark_models_begin,0,e_models_begin},
			{t_mark_model_begin,3,e_model_begin},
			{t_mark_objects_begin,0,t_mark_objects_begin}
		},
		//2
		{
			{t_id,1,e_create_model}
			
		},
		//3
		{
			{s_model_propertys,4,e_nothing}
			
		},
		//4
		{
			{t_mark_model_propertys_begin,5,e_model_propertys_begin}
			
			
		},
		//5
		{
			{s_property,6,e_nothing},
			{s_propertys_list,7,e_nothing},
			{t_mark_model_propertys_end ,12,e_model_propertys_end},
			{t_mark_objects_end,10,e_objects_end}
			
			
		},
		//6
		{
			{t_until_key_value,5,e_property_push}
			
			
		},
		//7
		{
			{t_mark_propertys_list_begin,8,e_propertys_list_begin},
			{t_mark_objects_end,10,e_objects_end},
			{t_mark_model_propertys_end ,12,e_model_propertys_end}
			
			
		},
		//8
		{
			{s_objects,9,e_nothing}
			
			
		},
		//9
		{
			{t_id,1,e_object_create_objectType}
			
		},
		
		//10
		{
			{t_mark_propertys_list_end,7,e_propertys_list_end},
			{s_objects,9,e_nothing}
			
			
			
		},
		//11
		{
			{t_mark_model_propertys_end ,12,e_model_propertys_end}
			
			
			
		}
		,
		//12
		{
			{t_mark_model_end ,0,e_model_end}
			
			
			
		}
		,
		//13
		{
			{t_mark_models_end ,0,e_models_end}
			
			
			
		}
//		//0
//		{
//			{t_models_begin,1,-1}
//		},
//		//1
//		{
//			{t_l_begin,2,-1}
//		},
//		//2
//		{
//			{t_model_begin,3,-1}
//		},
//		//3
//		{
//			{t_id,4,e_model_create}
//		}
//		,
//		//4
//		{
//			{t_s_begin,5,-1}
//		}
//		,
//		//5
//		{
//			{t_model_property_begin,4,-1},
//			{t_model_property,6,-1}
//		}
//		,
//		//6
//		{
//			{t_until,7,e_model_set_property}
//			
//			
//		}
//		,
//		//7
//		{
//			{t_id,5,e_model_set_property},
//			{t_model_property,6,-1}
//		
//			
//		}
	
		
};
}
