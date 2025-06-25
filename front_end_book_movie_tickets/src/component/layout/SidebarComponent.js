import {useState} from "react";
import {Film, MapPin,DoorOpen,CalendarDays} from "lucide-react";
import {Link} from "react-router-dom";
import '../../css/admin/adminSidebar.css'
import '../../css/admin/submenu.css'

const SidebarComponent = () => {
    const [openMenus, setOpenMenus] = useState({
        movie: false,
        cinema: false,
        room: false,
        shows:false
    });

    const toggleMenu = (menuName) => {
        setOpenMenus(prev => ({
            ...prev,
            [menuName]: !prev[menuName]
        }));
    };

    return (
        <div className={'admin-sidebar'}>
            <ul className="sidebar-menu">
                <li>
                    <div onClick={() => toggleMenu("movie")} className="menu-title">
                        <Film size={25}/>
                        <span style={{cursor: 'pointer'}}>Quản lý phim</span>
                    </div>
                    {openMenus.movie && (
                        <ul className="submenu">
                            <li><Link to="/admin/movie">Danh sách phim</Link></li>
                            <li><Link to="/admin/movie/create">Thêm phim</Link></li>
                        </ul>
                    )}
                </li>
                <li>
                    <div onClick={() => toggleMenu("cinema")} className="menu-title">
                        <MapPin  size={25}/>
                        <span style={{cursor: 'pointer'}}>Quản lý rạp</span>
                    </div>
                    {openMenus.cinema&& (
                        <ul className="submenu">
                            <li><Link to="/admin/cinema">Danh sách rạp</Link></li>
                            <li><Link to="/admin/cinema/create">Thêm rạp</Link></li>
                        </ul>
                    )}
                </li>
                <li>
                    <div onClick={() => toggleMenu("room")} className="menu-title">
                        <DoorOpen  size={25}/> <span style={{cursor: 'pointer'}}>Quản lý phòng chiếu</span>
                    </div>
                    {openMenus.room&& (
                        <ul className="submenu">
                            <li><Link to="/admin/room">Danh sách phòng chiếu</Link></li>
                            <li><Link to="/admin/room/create">Thêm phòng chiếu</Link></li>
                        </ul>
                    )}
                </li>
                <li>
                    <div onClick={() => toggleMenu("shows")} className="menu-title">
                        <CalendarDays  size={25}/> <span style={{cursor: 'pointer'}}>Quản lý xuất chiếu</span>
                    </div>
                    {openMenus.shows && (
                        <ul className="submenu">
                            <li><Link to="/admin/show">Danh sách xuất chiếu</Link></li>
                            <li><Link to="/admin/show/create">Thêm xuất chiếu</Link></li>
                        </ul>
                    )}
                </li>
            </ul>
        </div>
    );
}
export default SidebarComponent;