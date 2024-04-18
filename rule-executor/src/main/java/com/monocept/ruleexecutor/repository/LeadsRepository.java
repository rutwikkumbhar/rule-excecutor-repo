package com.monocept.ruleexecutor.repository;

import com.monocept.ruleexecutor.entity.Leads;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface LeadsRepository extends CrudRepository<Leads, String>, JpaSpecificationExecutor<Leads> {

    @Query(value = "select l.agentid, l.customername, l.leadid, ud.ssoid as userId, ud.firebaseId as deviceId,  count(l.leadid) as count from leads l join user_details ud  on ud.agentcode = l.agentid where l.callnotconnected = 'Call Ringing Not Answered' group by l.agentid, l.leadid, l.customername, ud.ssoid, ud.firebaseId", nativeQuery = true)
    public List<Map<String, Object>> nudge1();

    @Query(value = "select l.agentid, l.customername, l.leadid, ud.ssoid as userId, ud.firebaseId as deviceId, count(l.leadid) as count from leads l join user_details ud on ud.agentcode = l.agentid where l.leadpropensity  = 'High' or l.leadpropensity = 'Medium' and l.callconnected != 'Meeting Scheduled' group by l.agentid,l.leadid,l.customername,ud.ssoid, ud.firebaseId", nativeQuery = true)
    public List<Map<String, Object>> nudge2();

    @Query(value = "select l.agentid, l.customername,l.leadid,ud.ssoid as userId, ud.firebaseId as deviceId, count(q.qid) as count from leads l join user_details ud on ud.agentcode = l.agentid left outer join \"quote\" q on q.lead_id = l.leadid where l.leadpropensity = 'High' or l.leadpropensity = 'Medium' and l.callconnected != 'Meeting Scheduled' group by l.agentid, l.leadid, l.customername,ud.ssoid,ud.firebaseId", nativeQuery = true)
    public List<Map<String, Object>> nudge3();

    @Query(value = "select l.agentid,l.customername,l.leadid,ud.ssoid as userId,ud.firebaseId as deviceId, count(q.qid) as count from leads l join user_details ud on ud.agentcode = l.agentid left outer join \"quote\" q on q.lead_id = l.leadid where l.statuscodeid in ('100347', '100355', '100366') group by l.agentid, l.leadid, l.customername,ud.ssoid,ud.firebaseId", nativeQuery = true)
    public List<Map<String, Object>> nudge4();

    @Query(value = "select l.agentid,l.customername,l.leadid,ud.ssoid as userId,ud.firebaseId as deviceId, count(q.qid) as count from leads l join user_details ud on ud.agentcode = l.agentid left outer join \"quote\" q on\tq.lead_id = l.leadid where\tl.leadpropensity  = 'High' or l.leadpropensity = 'Medium' and l.statuscodeid in ('145', '100354') group by l.agentid, l.leadid, l.customername, ud.ssoid, ud.firebaseId", nativeQuery = true)
    public List<Map<String, Object>> nudge5();

}
