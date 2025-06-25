import SidebarComponent from "./SidebarComponent";
import {Outlet} from "react-router-dom";
import '../../css/admin/admin-layout.css'
import HeaderComponent from "../homePage/HeaderComponent";

const AdminLayout = () => {
    return (
        <div className="admin-layout">
            <div style={{display: "flex"}}>
                <HeaderComponent/>
                <SidebarComponent/>
                <div className="admin-content">
                    <div>
                        <Outlet/>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminLayout;